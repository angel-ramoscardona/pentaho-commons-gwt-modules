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


package org.pentaho.gwt.widgets.client.buttons;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.pentaho.gwt.widgets.client.utils.ElementUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings( "deprecation" )
public class RoundedButton extends Widget implements HasClickHandlers {

  private String text = ""; //$NON-NLS-1$
  private String baseStyleName = "roundedbutton"; //$NON-NLS-1$
  private Label label = new Label();
  private SimplePanel leftPanel = new SimplePanel();
  private SimplePanel rightPanel = new SimplePanel();
  private boolean enabled = true;

  protected List<ClickListener> listeners = new ArrayList<ClickListener>();
  protected Command command;

  public RoundedButton() {
    createElement();
    this.setStylePrimaryName( baseStyleName );
    sinkEvents( Event.MOUSEEVENTS );
    sinkEvents( Event.ONDBLCLICK );
  }

  public RoundedButton( final String text ) {
    this();
    setText( text );
  }

  public RoundedButton( final String text, Command command ) {
    this( text );
    this.command = command;
  }

  private void createElement() {

    HorizontalPanel hbox = new HorizontalPanel();
    this.setElement( hbox.getElement() );
    hbox.setStyleName( baseStyleName );

    super.setStylePrimaryName( baseStyleName );

    // Add in placeholder simplepanels
    leftPanel.setStylePrimaryName( this.getStylePrimaryName() );
    leftPanel.addStyleDependentName( "left" ); //$NON-NLS-1$
    hbox.add( leftPanel );
    hbox.add( label );
    rightPanel.setStylePrimaryName( this.getStylePrimaryName() );
    rightPanel.addStyleDependentName( "right" ); //$NON-NLS-1$
    hbox.add( rightPanel );

    // Set styles
    leftPanel.setStylePrimaryName( this.getStylePrimaryName() );
    rightPanel.setStylePrimaryName( this.getStylePrimaryName() );
    label.getElement().getParentElement().setClassName( this.getStylePrimaryName() + "-slice" ); //$NON-NLS-1$
    label.setStylePrimaryName( this.getStylePrimaryName() );
    label.addStyleDependentName( "label" ); //$NON-NLS-1$

    // prevent double-click from selecting text
    ElementUtils.preventTextSelection( label.getElement() );

  }

  @Override
  public void setStylePrimaryName( String style ) {
    super.setStylePrimaryName( style );
    baseStyleName = style;

    label.setStylePrimaryName( style + "-label" ); //$NON-NLS-1$
    rightPanel.setStylePrimaryName( style + "-right" ); //$NON-NLS-1$
    leftPanel.setStylePrimaryName( style + "-left" ); //$NON-NLS-1$
    label.getElement().getParentElement().setClassName( style + "-slice" ); //$NON-NLS-1$
  }

  @Override
  public void addStyleDependentName( String style ) {
    super.addStyleDependentName( style );

    label.addStyleDependentName( style );

    rightPanel.addStyleDependentName( style );
    leftPanel.addStyleDependentName( style );
    label.getElement().getParentElement().setClassName( this.getStylePrimaryName() + "-slice-" + style ); //$NON-NLS-1$
  }

  @Override
  public void removeStyleDependentName( String style ) {
    super.removeStyleDependentName( style );

    label.removeStyleDependentName( style );

    rightPanel.removeStyleDependentName( style );
    leftPanel.removeStyleDependentName( style );
    label.getElement().getParentElement().setClassName( this.getStylePrimaryName() + "-slice" ); //$NON-NLS-1$
  }

  public String getText() {
    return text;
  }

  public void setText( String text ) {
    this.text = text;
    label.setText( text );
  }

  public void setEnabled( boolean enabled ) {
    boolean prevVal = this.enabled;
    this.enabled = enabled;

    if ( prevVal && enabled ) {
      return;
    } else if ( !enabled ) {
      this.addStyleDependentName( "disabled" ); //$NON-NLS-1$
    } else if ( enabled ) {
      this.removeStyleDependentName( "disabled" ); //$NON-NLS-1$
    }
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void onBrowserEvent( Event event ) {
    switch ( event.getTypeInt() ) {
      case Event.ONMOUSEUP:
        if ( RoundedButton.this.isEnabled() ) {
          fireClicked();
          if ( command != null ) {
            command.execute();
          }
          removeStyleDependentName( "over" ); //$NON-NLS-1$
          event.cancelBubble( true );
          event.preventDefault();
        }
        break;
      case Event.ONDBLCLICK:
        event.cancelBubble( true );
        event.preventDefault();
        break;
      case Event.ONMOUSEOVER:
        if ( RoundedButton.this.isEnabled() ) {
          RoundedButton.this.addStyleDependentName( "over" ); //$NON-NLS-1$
        }
        break;
      case Event.ONMOUSEOUT:
        if ( RoundedButton.this.isEnabled() ) {
          RoundedButton.this.removeStyleDependentName( "over" ); //$NON-NLS-1$
        }
        break;

    }
  }

  public HandlerRegistration addClickHandler( ClickHandler handler ) {
    return addDomHandler( handler, ClickEvent.getType() );
  }

  private void fireClicked() {

    // Deprecated interface
    for ( ClickListener listener : listeners ) {
      listener.onClick( this );
    }

    // New 1.6 style events
    NativeEvent evt = Document.get().createClickEvent( 0, 0, 0, 0, 0, false, false, false, false );
    DomEvent.fireNativeEvent( evt, this );

  }

  @Deprecated
  public void addClickListener( ClickListener listener ) {
    listeners.add( listener );
  }

  @Deprecated
  public void removeClickListener( ClickListener listener ) {
    listeners.remove( listener );
  }

  public void setFocus( boolean focus ) {
    this.setFocus( focus );
  }

}
