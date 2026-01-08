package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import service.ApiConverter;
import service.ManualConverter;

public class ConverterController {

    @FXML
    private TextArea inputArea;

    @FXML
    private TextArea outputArea;

    private ApiConverter api = new ApiConverter();
    private ManualConverter manual = new ManualConverter();

    @FXML
    private void xmlToJsonApi() {
        outputArea.setText(api.xmlToJson(inputArea.getText()));
    }

    @FXML
    private void jsonToXmlApi() {
        outputArea.setText(api.jsonToXml(inputArea.getText()));
    }

    @FXML
    private void xmlToJsonManual() {
        outputArea.setText(manual.xmlToJson(inputArea.getText()));
    }

    @FXML
    private void jsonToXmlManual() {
        outputArea.setText(manual.jsonToXml(inputArea.getText()));
    }
}
