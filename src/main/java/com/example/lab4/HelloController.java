package com.example.lab4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class HelloController {

    @FXML
    private TextField classField;

    @FXML
    private GridPane classFields;

    private ArrayList<Object> newObjectFields = new ArrayList<>();
    private Object newObject;

    @FXML
    private TextArea areaSave;
    private static TextArea areaSaveStatic;

    @FXML
    void initialize() {
        HelloController.areaSaveStatic = areaSave;
    }

    @FXML
    void CreateObject(ActionEvent event) {
        try {
            newObject = Class.forName(classField.getText()).getConstructor().newInstance();
            var objectClass = newObject.getClass();
            var fields = objectClass.getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getName().toLowerCase().contains("text")) {
                    newObjectFields.add(new TextArea());
                    classFields.add((Node) newObjectFields.get(newObjectFields.size() - 1), 0, i);

                    var text = new Text(); // com.example.lab4.Pope
                    text.setText(" <- " + fields[i].getName());
                    classFields.add(text, 1, i);

                } else {
                    newObjectFields.add(new TextField());
                    classFields.add((Node) newObjectFields.get(newObjectFields.size() - 1), 0, i);

                    var text = new Text(); // com.example.lab4.Pope
                    text.setText(" <- " + fields[i].getName());
                    classFields.add(text, 1, i);
                }
            }

            loadObject();
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadObject() {
        Class<?> objectClass = newObject.getClass();
        var fields = objectClass.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            try {
                Method method = objectClass.getMethod("get" + fields[i].getName().substring(0,1).toUpperCase() + fields[i].getName().substring(1));

                if (newObjectFields.get(i).getClass() == TextField.class) {
                    try {
                        ((TextField) newObjectFields.get(i)).setText(method.invoke(newObject).toString());
                    } catch (Exception ignored) {
                    }
                }
                if (newObjectFields.get(i).getClass() == TextArea.class) {
                    try {
                        ((TextArea) newObjectFields.get(i)).setText(method.invoke(newObject).toString());
                    } catch (Exception ignored) {
                    }
                }
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void SaveObject(ActionEvent event) {
        var objectClass = newObject.getClass();
        var fields = objectClass.getDeclaredFields();

        var methods = objectClass.getMethods();
        var methodsNames = new ArrayList<String>();
        for (int i = 0; i < methods.length; i++) {
            methodsNames.add(methods[i].getName());
        }

        for (int i = 0; i < fields.length; i++) {
            Method method = null;
            var methodName = "set" + fields[i].getName().substring(0, 1).toUpperCase()
                    + fields[i].getName().substring(1);
            var parameterType = methods[methodsNames.indexOf(methodName)].getParameterTypes()[0];

            if (newObjectFields.get(i).getClass() == TextArea.class) {
                var textAreaElement = (TextArea) newObjectFields.get(i);
                try {
                    method = objectClass.getMethod(methodName, parameterType);
                    switch (parameterType.toString()) {
                        case "class java.lang.String":
                        case "class java.lang.string": {
                            method.invoke(newObject, textAreaElement.getText());
                            break;
                        }
                        case "class java.lang.Integer":
                        case "int": {
                            method.invoke(newObject, Integer.parseInt(textAreaElement.getText()));
                            break;
                        }
                        case "class java.lang.Boolean":
                        case "class java.lang.boolean": {
                            method.invoke(newObject, Boolean.parseBoolean(textAreaElement.getText()));
                            break;
                        }
                        case "class java.lang.Byte":
                        case "class java.lang.byte": {
                            method.invoke(newObject, Byte.parseByte(textAreaElement.getText()));
                            break;
                        }
                        case "class java.lang.Short":
                        case "class java.lang.short": {
                            method.invoke(newObject, Short.parseShort(textAreaElement.getText()));
                            break;
                        }
                        case "class java.lang.Long":
                        case "class java.lang.long": {
                            method.invoke(newObject, Long.parseLong(textAreaElement.getText()));
                            break;
                        }
                        case "class java.lang.Float":
                        case "class java.lang.float": {
                            method.invoke(newObject, Float.parseFloat(textAreaElement.getText()));
                            break;
                        }
                        case "class java.lang.Double":
                        case "class java.lang.double": {
                            method.invoke(newObject, Double.parseDouble(textAreaElement.getText()));
                            break;
                        }
                        case "class java.lang.Character": {
                            if (textAreaElement.getText().length() == 1) {
                                method.invoke(newObject, textAreaElement.getText().charAt(0));

                            } else {
                                throw new Exception("");
                            }
                            break;
                        }
                        case "class java.lang.character": {
                            if (textAreaElement.getText().length() == 1) {
                                method.invoke(newObject, textAreaElement.getText().charAt(0));
                            } else {
                                areaSaveStatic.setText(areaSaveStatic.getText() + "The property named " +
                                        fields[i].getName() + " can't be changed. \n");
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    areaSaveStatic.setText(areaSaveStatic.getText() + "The property named " +
                            fields[i].getName() + " can't be changed. \n");
                }
            } else {
                var textFieldElement = (TextField) newObjectFields.get(i);
                try {
                    method = objectClass.getMethod(methodName, parameterType);

                    switch (parameterType.toString()) {
                        case "class java.lang.String":
                        case "class java.lang.string": {
                            method.invoke(newObject, textFieldElement.getText());
                            break;
                        }
                        case "class java.lang.Integer":
                        case "int": {
                            method.invoke(newObject, Integer.parseInt(textFieldElement.getText()));
                            break;
                        }
                        case "class java.lang.Boolean":
                        case "class java.lang.boolean": {
                            method.invoke(newObject, Boolean.parseBoolean(textFieldElement.getText()));
                            break;
                        }
                        case "class java.lang.Byte":
                        case "class java.lang.byte": {
                            method.invoke(newObject, Byte.parseByte(textFieldElement.getText()));
                            break;
                        }
                        case "class java.lang.Short":
                        case "class java.lang.short": {
                            method.invoke(newObject, Short.parseShort(textFieldElement.getText()));
                            break;
                        }
                        case "class java.lang.Long":
                        case "class java.lang.long": {
                            method.invoke(newObject, Long.parseLong(textFieldElement.getText()));
                            break;
                        }
                        case "class java.lang.Float":
                        case "class java.lang.float": {
                            method.invoke(newObject, Float.parseFloat(textFieldElement.getText()));
                            break;
                        }
                        case "class java.lang.Double":
                        case "class java.lang.double": {
                            method.invoke(newObject, Double.parseDouble(textFieldElement.getText()));
                            break;
                        }
                        case "class java.lang.Character": {
                            if (textFieldElement.getText().length() == 1) {
                                method.invoke(newObject, textFieldElement.getText().charAt(0));
                                break;
                            } else {
                                areaSaveStatic.setText(areaSaveStatic.getText() + "The property named " +
                                        fields[i].getName() + " can't be changed. \n");
                                break;
                            }
                        }
                        case "class java.lang.character": {
                            if (textFieldElement.getText().length() == 1) {
                                method.invoke(newObject, textFieldElement.getText().charAt(0));
                            } else {
                                areaSaveStatic.setText(areaSaveStatic.getText() + "The property named " +
                                        fields[i].getName() + " can't be changed. \n");
                            }
                            break;
                        }
                    }

                } catch (Exception e) {
                    areaSaveStatic.setText(areaSaveStatic.getText() + "The property named " +
                            fields[i].getName() + " can't be changed. \n");
                }
            }
        }
        printTextToConsole();
    }

    private void printTextToConsole() {
        var text = areaSaveStatic.getText() + "Object class: " + newObject.getClass();
        var classOfObject = newObject.getClass();
        var fields = classOfObject.getDeclaredFields();
        var methods = classOfObject.getMethods();

        for (int i = 0; i < fields.length; i++) {
            text += fields[i].getName() + "=";
            var methodsNames = new ArrayList<String>();
            for (int j = 0; j < methods.length; j++) {
                methodsNames.add(methods[j].getName());
            }

            var methodName = "get" + fields[i].getName().substring(0, 1).toUpperCase()
                    + fields[i].getName().substring(1);
            var method = methods[methodsNames.indexOf(methodName)];
            try {
                text += method.invoke(newObject) + "\n";
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        areaSaveStatic.setText(text);
    }
}
