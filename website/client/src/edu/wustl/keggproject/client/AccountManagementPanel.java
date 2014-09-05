/* 
 * Copyright (c) 2011, Eric Xu, Xueyang Feng
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, 
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice, 
 *  this list of conditions and the following disclaimer in the documentation 
 *  and/or other materials provided with the distribution.
 *  
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 *  POSSIBILITY OF SUCH DAMAGE.
 */

package edu.wustl.keggproject.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

import edu.wustl.keggproject.client.datasource.AccountSummaryDS;

public class AccountManagementPanel {

	private DialogBox mydia;

	public SimplePanel accountManagementPanel = new SimplePanel();

	public Widget getAccountManagementPanel() {
		return accountManagementPanel;
	}

	public void setDiaglog(DialogBox d) {
		mydia = d;
	}
	
	public void initialize() {
		accountManagementPanel.setVisible(false);
	}

	public void ChangeToSummary() {
		accountManagementPanel.clear();
		accountManagementPanel.setVisible(true);

		VerticalPanel summaryPanel = new VerticalPanel();

		final ListGrid accountSummary = new ListGrid();
		accountSummary.setWidth(400);
		accountSummary.setHeight(500);
		accountSummary.setShowAllRecords(true);
		accountSummary.setDataSource(AccountSummaryDS.getInstance());

		ListGridField date = new ListGridField("date");
		ListGridField model = new ListGridField("model");
		ListGridField type = new ListGridField("type");
		ListGridField status = new ListGridField("status");
		// ListGridField url = new ListGridField("url");

		accountSummary.setFields(date, model, type, status);
		accountSummary.setAutoFetchData(true);

		Button buttonExit = new Button("Exit Summary");
		buttonExit.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// accountManagementPanel.setVisible(false);
				mydia.hide();
			}
		});

		summaryPanel.add(accountSummary);
		summaryPanel.add(buttonExit);
		accountManagementPanel.setWidget(summaryPanel);
	}

	public void ChangeToPasswordChange() {
		accountManagementPanel.clear();
		accountManagementPanel.setVisible(true);

		final FormPanel changePassword = new FormPanel();
		changePassword.setAction(ConfigurationFactory.getConfiguration()
				.getBaseUrl() + "user/password/change/");

		changePassword.setMethod(FormPanel.METHOD_POST);

		Grid changePasswordGrid = new Grid(3, 2);
		Label newPassword = new Label("New Password");
		final PasswordTextBox newPasswordBox = new PasswordTextBox();
		Label confirmPassword = new Label("Confirm Password");
		final PasswordTextBox confirmPasswordBox = new PasswordTextBox();
		Button changeButton = new Button("Change Password");
		Button cancelButton = new Button("Cancel");

		newPasswordBox.setName("newpassword");
		confirmPasswordBox.setName("confirmpassword");

		changePasswordGrid.setWidget(0, 0, newPassword);
		changePasswordGrid.setWidget(0, 1, newPasswordBox);
		changePasswordGrid.setWidget(1, 0, confirmPassword);
		changePasswordGrid.setWidget(1, 1, confirmPasswordBox);
		changePasswordGrid.setWidget(2, 0, changeButton);
		changePasswordGrid.setWidget(2, 1, cancelButton);

		changePassword.add(changePasswordGrid);
		accountManagementPanel.setWidget(changePassword);

		changeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (newPasswordBox.getText().isEmpty()
						|| newPasswordBox.getText().isEmpty()) {
					Window.alert("Please check the new password");
					return;
				}

				if (!newPasswordBox.getText().equals(
						confirmPasswordBox.getText())) {
					Window.alert("Please confirm the new password");
					return;
				}
				changePassword.submit();
				// accountManagementPanel.setVisible(false);
				mydia.hide();
			}

		});

		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// accountManagementPanel.setVisible(false);
				mydia.hide();
			}
		});

		changePassword.addSubmitHandler(new FormPanel.SubmitHandler() {

			@Override
			public void onSubmit(SubmitEvent event) {
				;
			}
		});

		changePassword
				.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						if (event.getResults().contains("successfully")) {
							Window.alert("Password changed");
						}
					}
				});
	}
}
