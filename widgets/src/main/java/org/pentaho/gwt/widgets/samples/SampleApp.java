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


package org.pentaho.gwt.widgets.samples;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.DragController;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.controls.ColorPicker;
import org.pentaho.gwt.widgets.client.controls.ColorPickerListener;
import org.pentaho.gwt.widgets.client.listbox.CustomListBox;
import org.pentaho.gwt.widgets.client.listbox.DefaultListItem;
import org.pentaho.gwt.widgets.client.ui.Draggable;

/**
 * Created by IntelliJ IDEA. User: Nick Baker Date: Mar 9, 2009 Time: 12:54:29 PM
 */
@SuppressWarnings( "nls" )
public class SampleApp implements EntryPoint {
  @SuppressWarnings( "deprecation" )
  public void onModuleLoad() {

    DragController dragController = new PickupDragController( RootPanel.get(), false ) {

      {
        setBehaviorDragProxy( true );
        setBehaviorDragStartSensitivity( 5 );

      }
      private Widget proxy;

      @Override
      protected void restoreSelectedWidgetsStyle() {
      }

      @Override
      protected void saveSelectedWidgetsLocationAndStyle() {
      }

      @Override
      protected void restoreSelectedWidgetsLocation() {
      }

      @Override
      public void dragStart() {
        super.dragStart();
      }

      @Override
      public void dragEnd() {
        proxy.removeFromParent();
        proxy = null;
      }

      @Override
      protected Widget newDragProxy( DragContext context ) {
        proxy = ( (Draggable) context.draggable ).makeProxy( context.draggable );
        return proxy;
      }

      @Override
      public void previewDragEnd() throws VetoDragException {

      }
    };

    final CustomListBox list = new CustomListBox();

    list.setDragController( dragController );

    list.addItem( "Alberta" );
    list.addItem( "Atlanta" );
    list.addItem( "San Francisco" );
    list.addItem( "Alberta" );
    list.addItem( "Atlanta" );
    list.addItem( "San Francisco" );
    list.addItem( "Alberta" );
    list.addItem( "Atlanta" );
    list.addItem( "San Francisco" );
    list.addItem( "Alberta" );
    list.addItem( new DefaultListItem( "Testing", new Image( "16x16sample.png" ) ) );
    list.addItem( new DefaultListItem( "Testing 2", new CheckBox() ) );

    // list.setVisibleRowCount(6);

    list.addChangeListener( new ChangeListener() {
      public void onChange( Widget widget ) {
        System.out.println( "" + list.getSelectedIndex() );
      }
    } );

    list.setWidth( "100%" );
    list.setHeight( "100%" );

    // RootPanel.get().add(new Label("Combo: "));
    // RootPanel.get().add(list);

    final CustomListBox list2 = new CustomListBox();

    list2.setDragController( dragController );

    list2.addItem( "Alberta" );
    list2.addItem( "Atlanta" );
    list2.addItem( "San Francisco" );
    list2.addItem( "Alberta" );
    list2.addItem( "Atlanta" );
    list2.addItem( "San Francisco" );
    list2.addItem( "Alberta" );
    list2.addItem( "Atlanta" );
    list2.addItem( "San Francisco" );
    list2.addItem( "Alberta" );
    list2.addItem( "Atlanta" );
    list2.addItem( "San Francisco" );
    list2.add( "Alberta" );
    list2.add( "Atlanta" );
    list2.add( "San Francisco" );
    list2.add( "Alberta" );
    list2.add( "Atlanta" );
    list2.add( "San Francisco" );
    list2.add( new DefaultListItem( "Testing", new Image( "16x16sample.png" ) ) );
    list2.addItem( new DefaultListItem( "Testing 2", new CheckBox() ) );
    list2.setVisibleRowCount( 6 );
    // list2.setEditable(true);
    list2.setValue( "Bogus" );

    list2.setMultiSelect( true );

    final Label selected = new Label();
    list2.addChangeListener( new ChangeListener() {
      @Override
      public void onChange( Widget widget ) {
        int[] sels = list2.getSelectedIndices();
        selected.setText( "" );
        for ( int i = 0; i < sels.length; i++ ) {
          selected.setText( selected.getText() + ", " + sels[i] );
        }
      }
    } );

    RootPanel.get().add( new Label( "" ) );
    RootPanel.get().add( new Label( "Combo2: " ) );
    RootPanel.get().add( list2 );
    RootPanel.get().add( selected );

    CustomListBox list3 = new CustomListBox();

    DefaultListItem dli = null;
    dli = new DefaultListItem( "Testing 1", new Image( "16x16sample.png" ) );
    dli.setValue( "Value of Testing 1" );
    list3.add( dli );

    dli = new DefaultListItem( "Testing 2", new CheckBox() );
    dli.setValue( "Value of Testing 2" );
    list3.add( dli );

    // RootPanel.get().add(new Label(""));
    // RootPanel.get().add(new Label("Combo3: "));
    // RootPanel.get().add(list3);

    Label showSelectedLabel = new Label( "Selected item's value:" );

    final TextBox showSelectedTextBox = new TextBox();
    showSelectedTextBox.setReadOnly( true );

    list3.addChangeListener( new ChangeListener() {

      public void onChange( Widget widget ) {
        String val = (String) ( (CustomListBox) widget ).getSelectedItem().getValue();
        if ( val != null ) {
          showSelectedTextBox.setText( val );
        }
      }
    } );
    final CustomListBox list4 = new CustomListBox();

    list4.addItem( "Albert" );
    list4.addItem( "Greg" );
    list4.setWidth( "170px" );

    RootPanel.get().add( new Label( "" ) );
    RootPanel.get().add( new Label( "Combo4: " ) );
    RootPanel.get().add( list4 );

    RootPanel.get().add( showSelectedLabel );
    RootPanel.get().add( showSelectedTextBox );

    final ColorPicker picker = new ColorPicker();
    picker.addColorPickerListener( new ColorPickerListener() {

      public void colorPicked( ColorPicker picker ) {
        System.out.println( "color: " + picker.getColor() );

      }

    } );

    RootPanel.get().add( picker );
    Button btn = new Button( "colorPicker" );
    RootPanel.get().add( btn );

    btn.addClickHandler( new ClickHandler() {

      public void onClick( ClickEvent event ) {
        picker.showPicker();

      }

    } );
  }
}
