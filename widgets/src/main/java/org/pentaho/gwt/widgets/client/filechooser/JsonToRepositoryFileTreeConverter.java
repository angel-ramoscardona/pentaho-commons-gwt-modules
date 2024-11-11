/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2028-08-13
 ******************************************************************************/


package org.pentaho.gwt.widgets.client.filechooser;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tkafalas
 * 
 */
public class JsonToRepositoryFileTreeConverter {
  String jsonText;

  public RepositoryFileTree getRepositoryFileTree( JSONObject serviceCallObject ) {
    RepositoryFileTree fileTree = new RepositoryFileTree();
    RepositoryFile rf = new RepositoryFile( serviceCallObject.get( "file" ).isObject() );
    fileTree.setFile( rf );

    // load up the model for the tree
    processChildren( fileTree, serviceCallObject );

    return fileTree;
  }

  public static final List<RepositoryFile> getTrashFiles( String jsonData ) {
    return getFileListFromJson( jsonData );
  }

  public static final List<RepositoryFile> getFileListFromJson( String jsonData ) {
    JSONObject jsonObject = JSONParser.parseLenient( jsonData ).isObject();
    List<RepositoryFile> files = new ArrayList<RepositoryFile>();
    JSONArray jsa = jsonObject.get( "repositoryFileDto" ).isArray();

    for ( int i = 0; i < jsa.size(); i++ ) {
      RepositoryFile trashItem = new RepositoryFile( jsa.get( i ).isObject() );
      files.add( trashItem );
    }
    return files;
  }

  /**
   * @param String
   */
  public JsonToRepositoryFileTreeConverter( String jsonText ) {
    super();
    // System.out.println(jsonText);
    this.jsonText = jsonText;
  }

  /**
   * @return
   */
  public RepositoryFileTree getTree() {
    JSONObject serviceCallObj = JSONParser.parseLenient( jsonText ).isObject();
    RepositoryFileTree value = getRepositoryFileTree( serviceCallObj );
    return value;
  }

  private static final RepositoryFileTree processChildren( RepositoryFileTree parent, JSONObject jsoFolderContents ) {
    JSONValue jsvChildren = jsoFolderContents.get( "children" );
    List<RepositoryFileTree> children = new ArrayList<RepositoryFileTree>();
    if ( jsvChildren != null ) {
      JSONArray jsaChildren = jsvChildren.isArray();
      for ( int i = 0; i < jsaChildren.size(); i++ ) {
        RepositoryFileTree repositoryFileTree = new RepositoryFileTree();
        JSONObject child = jsaChildren.get( i ).isObject();

        // process the folder name for the children, or the individual file as a single child
        JSONValue childValue = child.get( "file" );
        RepositoryFile repositoryFile = new RepositoryFile( childValue.isObject() );
        repositoryFileTree.setFile( repositoryFile );
        if ( !repositoryFile.isFolder() ) {
          // Adding single file to the tree
          children.add( repositoryFileTree );
        } else {
          // process the children, if any
          childValue = child.get( "children" );
          if ( childValue != null ) {
            RepositoryFileTree childChildren = processChildren( repositoryFileTree, child );
            // System.out.println("Adding folder " + childChildren.getFile().getPath()
            // + " to " + parent.getFile().getPath());
            children.add( childChildren );
          } else {
            // we have to add empty folders too
            repositoryFileTree.setChildren( new ArrayList<RepositoryFileTree>() );
            children.add( repositoryFileTree );
          }
        }
      }
    }
    parent.setChildren( children );
    return parent;
  }
}
