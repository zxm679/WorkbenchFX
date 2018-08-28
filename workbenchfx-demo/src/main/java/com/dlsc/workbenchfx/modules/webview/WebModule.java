package com.dlsc.workbenchfx.modules.webview;

import com.dlsc.workbenchfx.model.WorkbenchModule;
import com.dlsc.workbenchfx.view.controls.ToolbarItem;
import com.google.common.base.Strings;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebModule extends WorkbenchModule {

  private final String url;
  private final WebView browser;
  private final TextField browserUrl;
  private WebEngine webEngine;

  public WebModule(String name, String url) {
    super(name, FontAwesomeIcon.GLOBE);
    this.url = url;

    // setup webview
    browser = new WebView();
    webEngine = browser.getEngine();
    webEngine.setOnStatusChanged(
        event -> System.out.println("Status of WebView changed to: " + event.getData()));
    webEngine.load(url);

    // setup toolbar
    browserUrl = new TextField("Loading...");
    HBox.setHgrow(browserUrl, Priority.ALWAYS);
    browserUrl.setEditable(false);
    ToolbarItem home = new ToolbarItem(new MaterialDesignIconView(MaterialDesignIcon.HOME),
        event -> webEngine.load(url));
    ToolbarItem increaseSize = new ToolbarItem(new MaterialDesignIconView(MaterialDesignIcon.PLUS),
        event -> browser.setFontScale(browser.getFontScale() + 1));
    ToolbarItem decreaseSize = new ToolbarItem(new MaterialDesignIconView(MaterialDesignIcon.MINUS),
        event -> browser.setFontScale(browser.getFontScale() - 1));
    getToolbarControlsLeft().addAll(home, increaseSize, decreaseSize, new ToolbarItem(browserUrl));

    // update textfield with url every time the url of the webview changes
    webEngine.documentProperty().addListener(
        observable -> {
          String currentUrl = webEngine.getDocument().getDocumentURI();
          if (!Strings.isNullOrEmpty(currentUrl)) {
            browserUrl.setText(currentUrl);
          }
        });
  }

  @Override
  public Node activate() {
    return browser;
  }

}
