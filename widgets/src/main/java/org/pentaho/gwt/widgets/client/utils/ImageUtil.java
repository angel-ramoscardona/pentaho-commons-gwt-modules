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


package org.pentaho.gwt.widgets.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;

/**
 * User: RFellows Date: 5/13/13
 */
public class ImageUtil {

  private static final ImageUtil INSTANCE = new ImageUtil();

  protected ImageUtil() {
  }

  public static final String BLANK_IMAGE_PATH = GWT.getHostPageBaseURL()
      + "content/common-ui/resources/themes/images/spacer.gif";

  public static ImageUtil getInstance() {
    return INSTANCE;
  }

  protected String getBlankImagePath() {
    return BLANK_IMAGE_PATH;
  }

  /**
   * Returns a GWT Image with the src value set to a blank image and applies the provided css style to the element to
   * allow for a background image to be used instead.
   * 
   * @param cssStyleName
   * @return
   */
  public static Image getThemeableImage( String... cssStyleName ) {
    Image image = new Image( getInstance().getBlankImagePath() );

    for ( String style : cssStyleName ) {
      image.addStyleName( style );
    }

    return image;
  }

}
