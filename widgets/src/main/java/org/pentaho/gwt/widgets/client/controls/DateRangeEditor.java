/*! ******************************************************************************
 *
 * Pentaho
 *
 * Copyright (C) 2024 by Hitachi Vantara, LLC : http://www.pentaho.com
 *
 * Use of this software is governed by the Business Source License included
 * in the LICENSE.TXT file.
 *
 * Change Date: 2029-07-20
 ******************************************************************************/


package org.pentaho.gwt.widgets.client.controls;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import org.pentaho.gwt.widgets.client.messages.Messages;
import org.pentaho.gwt.widgets.client.panel.HorizontalFlexPanel;
import org.pentaho.gwt.widgets.client.panel.VerticalFlexPanel;
import org.pentaho.gwt.widgets.client.ui.ICallback;
import org.pentaho.gwt.widgets.client.ui.IChangeHandler;

import java.util.Date;

/**
 * @author Steven Barkdull
 * 
 */
@SuppressWarnings( "deprecation" )
public class DateRangeEditor extends CaptionPanel implements IChangeHandler {

  private static final String SCHEDULE_EDITOR_CAPTION_PANEL = "schedule-editor-caption-panel"; //$NON-NLS-1$

  private static final String END_DATE_RB_GROUP = "end-date-group"; //$NON-NLS-1$
  private static final String END_DATE_PICKER = "end-date-picker"; //$NON-NLS-1$
  private static final String START_DATE_PICKER = "start-date-picker"; //$NON-NLS-1$
  private static final String ROR_INNER_HP = "ror-inner-hp";

  private DatePickerEx startDatePicker = null;
  private EndDatePanel endDatePanel = null;

  private ErrorLabel startLabel = null;
  private ICallback<IChangeHandler> onChangeHandler = null;
  private static int uniqueInstanceNumber = 0;

  public DateRangeEditor( Date date ) {

    super( Messages.getString( "DateRangeEditor.rangeOfRecurrence" ) );
    this.addStyleName( SCHEDULE_EDITOR_CAPTION_PANEL );
    uniqueInstanceNumber += 1;

    HorizontalFlexPanel outerHP = new HorizontalFlexPanel();
    add( outerHP );

    HorizontalFlexPanel hp = new HorizontalFlexPanel();
    hp.getElement().setId( ROR_INNER_HP );
    Label l = new Label( Messages.getString( "DateRangeEditor.startLabel" ) );
    l.setStyleName( "startLabel" ); //$NON-NLS-1$
    hp.add( l );
    DefaultFormat format = new DefaultFormat( DateTimeFormat.getShortDateFormat() );
    startDatePicker = new DatePickerEx( format );
    startDatePicker.getDatePicker().setStyleName( START_DATE_PICKER );
    hp.add( startDatePicker.getDatePicker() );
    startLabel = new ErrorLabel( hp );
    outerHP.add( startLabel );

    endDatePanel = new EndDatePanel( date );
    outerHP.add( endDatePanel );

    reset( date );
    configureOnChangeHandler();
  }

  public void setStartDateError( String errorMsg ) {
    startLabel.setErrorMsg( errorMsg );
  }

  public Date getStartDate() {
    return startDatePicker.getSelectedDate();
  }

  public void setStartDate( Date d ) {
    startDatePicker.getDatePicker().setValue( d );
  }

  public Date getEndDate() {
    return endDatePanel.getDate();
  }

  public void setEndDate( Date d ) {
    endDatePanel.setDate( d );
  }

  public void reset( Date d ) {
    startDatePicker.getDatePicker().setValue( d );
    endDatePanel.reset( d );
  }

  public void setNoEndDate() {
    endDatePanel.setNoEndDate();
  }

  public boolean isEndBy() {
    return endDatePanel.isEndBy();
  }

  public void setEndBy() {
    endDatePanel.setEndBy();
  }

  public boolean isNoEndDate() {
    return endDatePanel.isNoEndDate();
  }

  public void setEndByError( String errorMsg ) {
    endDatePanel.setEndByError( errorMsg );
  }

  public void setOnChangeHandler( ICallback<IChangeHandler> handler ) {
    this.onChangeHandler = handler;
  }

  private void changeHandler() {
    if ( null != onChangeHandler ) {
      onChangeHandler.onHandle( this );
    }
  }

  private void configureOnChangeHandler() {
    final DateRangeEditor localThis = this;

    ICallback<IChangeHandler> handler = new ICallback<IChangeHandler>() {
      public void onHandle( IChangeHandler o ) {
        localThis.changeHandler();
      }
    };

    startDatePicker.setOnChangeHandler( handler );
    endDatePanel.setOnChangeHandler( handler );
  }

  private class EndDatePanel extends VerticalFlexPanel implements IChangeHandler {

    private DatePickerEx endDatePicker = null;
    private RadioButton noEndDateRb = null;
    private RadioButton endByRb = null;
    private ErrorLabel endByLabel = null;
    private ICallback<IChangeHandler> onChangeHandler = null;
    private static final String END_DATE_PANEL = "end-date-panel";

    public EndDatePanel( Date date ) {
      this.getElement().setId( END_DATE_PANEL );
      final EndDatePanel localThis = this;

      noEndDateRb =
          new RadioButton( END_DATE_RB_GROUP + uniqueInstanceNumber, Messages.getString(
              "DateRangeEditor.noEndDateLabel" ) );
      noEndDateRb.setStyleName( "recurrenceRadioButton" ); //$NON-NLS-1$
      noEndDateRb.setValue( true );
      add( noEndDateRb );
      HorizontalFlexPanel hp = new HorizontalFlexPanel();
      add( hp );

      HorizontalFlexPanel endByPanel = new HorizontalFlexPanel();
      endByRb =
          new RadioButton( END_DATE_RB_GROUP + uniqueInstanceNumber, Messages.getString(
              "DateRangeEditor.endByLabel" ) );
      endByRb.setStyleName( "recurrenceRadioButton" ); //$NON-NLS-1$
      endByPanel.add( endByRb );
      DefaultFormat format = new DefaultFormat( DateTimeFormat.getShortDateFormat() );
      endDatePicker = new DatePickerEx( format );
      endDatePicker.getDatePicker().setStyleName( END_DATE_PICKER );
      endDatePicker.getDatePicker().setEnabled( false );
      endByPanel.add( endDatePicker.getDatePicker() );
      endByLabel = new ErrorLabel( endByPanel );
      hp.add( endByLabel );

      noEndDateRb.addClickListener( new ClickListener() {
        public void onClick( Widget sender ) {
          localThis.endDatePicker.getDatePicker().setEnabled( false );
        }
      } );

      endByRb.addClickListener( new ClickListener() {
        public void onClick( Widget sender ) {
          localThis.endDatePicker.getDatePicker().setEnabled( true );
        }
      } );
      reset( date );
      configureOnChangeHandler();
    }

    public void reset( Date d ) {
      setNoEndDate();
      endDatePicker.getDatePicker().setValue( d );
    }

    @SuppressWarnings( "unused" )
    public DatePickerEx getEndDatePicker() {
      return endDatePicker;
    }

    public void setNoEndDate() {
      endByRb.setValue( false );
      noEndDateRb.setValue( true );
      endDatePicker.getDatePicker().setEnabled( false );
    }

    public boolean isEndBy() {
      return endByRb.getValue();
    }

    public void setEndBy() {
      noEndDateRb.setValue( false );
      endByRb.setValue( true );
      endDatePicker.getDatePicker().setEnabled( true );
    }

    public boolean isNoEndDate() {
      return noEndDateRb.getValue();
    }

    public Date getDate() {
      return isEndBy() ? endDatePicker.getSelectedDate() : null;
    }

    public void setDate( Date d ) {
      endDatePicker.getDatePicker().setValue( d );
    }

    public void setEndByError( String errorMsg ) {
      endByLabel.setErrorMsg( errorMsg );
    }

    public void setOnChangeHandler( ICallback<IChangeHandler> handler ) {
      this.onChangeHandler = handler;
    }

    private void changeHandler() {
      if ( null != onChangeHandler ) {
        onChangeHandler.onHandle( this );
      }
    }

    private void configureOnChangeHandler() {
      final EndDatePanel localThis = this;

      ICallback<IChangeHandler> handler = new ICallback<IChangeHandler>() {
        public void onHandle( IChangeHandler o ) {
          localThis.changeHandler();
        }
      };
      KeyboardListener keyboardListener = new KeyboardListener() {
        public void onKeyDown( Widget sender, char keyCode, int modifiers ) {
        }

        public void onKeyPress( Widget sender, char keyCode, int modifiers ) {
        }

        public void onKeyUp( Widget sender, char keyCode, int modifiers ) {
          localThis.changeHandler();
        }
      };

      ClickListener clickListener = new ClickListener() {
        public void onClick( Widget sender ) {
          localThis.changeHandler();
        }
      };

      endDatePicker.setOnChangeHandler( handler );
      noEndDateRb.addClickListener( clickListener );
      noEndDateRb.addKeyboardListener( keyboardListener );
      endByRb.addClickListener( clickListener );
      endByRb.addKeyboardListener( keyboardListener );
    }
  } // end EndDatePanel
}
