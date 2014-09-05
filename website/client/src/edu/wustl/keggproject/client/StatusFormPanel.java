/* 
 * Copyright (c) 2011, Eric Xu
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
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class StatusFormPanel {
	private HorizontalPanel hp;
	private Label status;
	private FormPanel f;

	public StatusFormPanel() {
		hp = new HorizontalPanel();
		status = new Label("");
		f = new FormPanel();
		hp.add(status);
		hp.add(f);
	}
	
	public void initialize() {
	}
	
	public void clearAll() {
		hp.clear();
		status.setText("");
		f.clear();
	}

	public Widget getStatusFormPanel() {
		return hp;
	}

	public void clearStatus() {
		status.setText("");
	}

	public void clearForm() {
		f.clear();
	}

	public void setStatus(String s) {
		status.setText(s);
	}

	public void show() {
		hp.setHeight("40px");
	}

	public void hide() {
		hp.setHeight("0px");
	}

	public void loadFile() {
		clearStatus();
		clearForm();
		final Configuration conf = ConfigurationFactory.getConfiguration();
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET,
				conf.getBaseUrl() + "collection/list/");

		final ListBox collectionListBox = new ListBox();
		collectionListBox.setName("collection_name");
		collectionListBox.setVisible(false);

		setStatus("Fetching a list of models from server ... ");

		HorizontalPanel loadPanel = new HorizontalPanel();
		loadPanel.add(collectionListBox);
		final Button loadButton = new Button("Load");
		loadButton.setVisible(false);

		class MyCallback implements RequestCallback {

			@Override
			public void onResponseReceived(Request request, Response response) {
				collectionListBox.clear();
				collectionListBox.setVisible(true);
				loadButton.setVisible(true);
				for (String i : response.getText().split(" ")) {
					collectionListBox.addItem(i);
				}
				setStatus("Load model: ");
			}

			@Override
			public void onError(Request request, Throwable exception) {
				// TODO Auto-generated method stub
			}

		}

		try {
			rb.sendRequest("", new MyCallback());
		} catch (RequestException e) {
			e.printStackTrace();
		}

		loadPanel.add(loadButton);

		f.clear();
		f.setWidget(loadPanel);
		f.setAction(conf.getBaseUrl() + "collection/select/");
		f.setMethod(FormPanel.METHOD_GET);

		f.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				if (event.getResults().contains("selected")) {
					setStatus("Model "
							+ collectionListBox.getValue(collectionListBox
									.getSelectedIndex()) + " loaded. ");
					conf.setCurrentCollection(collectionListBox
							.getValue(collectionListBox.getSelectedIndex()));

				} else {
					setStatus("Model "
							+ collectionListBox.getValue(collectionListBox
									.getSelectedIndex()) + " is not loaded. ");

				}
				clearForm();
			}
		});

		f.addSubmitHandler(new FormPanel.SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				;
			}
		});

		loadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setStatus("Loading  "
						+ collectionListBox.getValue(collectionListBox
								.getSelectedIndex()));
				collectionListBox.setVisible(false);
				loadButton.setVisible(false);
				f.submit();
			}
		});

	}

	public void saveFile(boolean s) {
		final boolean in_background = s;
		final Configuration conf = ConfigurationFactory.getConfiguration();
		if (!in_background) {
			clearStatus();
			clearForm();

			setStatus("Saving model: " + conf.getCurrentCollection() + " ... ");
		}

		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET,
				conf.getBaseUrl() + "collection/save/");

		class MyCallback2 implements RequestCallback {

			@Override
			public void onResponseReceived(Request request, Response response) {
				if (response.getText().contains("saved") && !in_background) {
					setStatus("Model saved");
				}
			}

			@Override
			public void onError(Request request, Throwable exception) {
				// TODO Auto-generated method stub
			}

		}
		try {
			rb.sendRequest("", new MyCallback2());
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}

	public void saveFileAs() {
		clearStatus();
		clearForm();
		setStatus("Saving current model as: ");
		f.setVisible(true);
		final Configuration conf = ConfigurationFactory.getConfiguration();
		f.setAction(conf.getBaseUrl() + "collection/saveas/");
		f.setMethod(FormPanel.METHOD_GET);

		final HorizontalPanel saveaspanel = new HorizontalPanel();

		final TextBox newname = new TextBox();
		newname.setName("new_name");

		final Button saveasButton = new Button("Save as");
		saveaspanel.add(newname);
		saveaspanel.add(saveasButton);

		saveasButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setStatus(" Saving model as " + newname.getText());
				newname.setVisible(false);
				saveasButton.setVisible(false);
				f.submit();
			}
		});

		f.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				if (event.getResults().contains("renamed")) {
					setStatus("Model " + newname.getText() + " saved. ");

				} else {
					setStatus("Model " + newname.getText() + " is not saved. ");
				}
				clearForm();
			}
		});

		f.addSubmitHandler(new FormPanel.SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				// saveaspanel.setVisible(false);
			}

		});
		f.setWidget(saveaspanel);
	}
}
