package controllers;


import domain.Candidate;
import exceptions.ValidatorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import services.CandidateService;
import utils.ListEvent;
import utils.Observer;
import view.CandidateView;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CandidateController implements Observer<Candidate> {

    protected CandidateService service;
    private ObservableList<Candidate> model;

    private CandidateView view;

    public CandidateController(CandidateService service) {
        this.service = service;
        model= FXCollections.observableArrayList(service.getAllCandidates());
    }

    @Override
    public void notifyEvent(ListEvent<Candidate> e) {
        model.setAll(StreamSupport.stream(e.getList().spliterator(),false)
                .collect(Collectors.toList()));  // important
    }

    public CandidateView getView() {
        return view;
    }

    public void setView(CandidateView view) {
        this.view = view;
    }

    public ObservableList<Candidate> getModel() {
        return model;
    }

    public void handleAddMessage(ActionEvent actionEvent)
    {
        if(fieldsEmpty()){
            showErrorMessage("All the fields must be completed.");
        }
        else {
            Candidate toAdd = extractCandidate();
            try {
                Candidate returned = service.saveCandidate(toAdd);
                if (returned == null) {
                    showMessage(Alert.AlertType.INFORMATION, "Object added", "The object has been added.");
                } else {
                    showErrorMessage("It already exists a candidate with id  " + returned.getId());
                }
            } catch (ValidatorException e) {
                showErrorMessage("Validation exception: " + e.getMessage());
            }
        }
    }

    public void handleUpdateMessage(ActionEvent actionEvent) {
        if(fieldsEmpty()){
            showErrorMessage("All the fields must be completed.");
        }
        else {
            Candidate candidateToUpdate = extractCandidate();
            try {
                Candidate returned = service.updateCandidate(candidateToUpdate);
                if (returned == null) {
                    showMessage(Alert.AlertType.INFORMATION, "Object updated", "The object has been updated.");
                } else {
                    showErrorMessage("It doesn't exist a candidate with id given.");
                }
            } catch (ValidatorException e) {
                showErrorMessage("Validation exception: " + e.getMessage());
            }
        }
    }

    public void handleClearFields(ActionEvent actionEvent) {
        view.getTxtfieldID().clear();
        view.getTxtfieldFirstName().clear();
        view.getTxtfieldLastName().clear();
        view.getTxtfieldPhoneNr().clear();
        view.getTxtfieldMail().clear();
        view.getTxtfieldID().setDisable(false);
    }

    public void handleDeleteMessage(ActionEvent actionEvent) {
        if(Objects.equals(view.getTxtfieldID().getText(), "")){
            showErrorMessage("The id field must contain a value.");
        }else {
            int idCandidateToBeDeleted = Integer.parseInt(view.getTxtfieldID().getText());
            Candidate returned = service.deleteCandidate(idCandidateToBeDeleted);
            if (returned != null) {
                showMessage(Alert.AlertType.INFORMATION, "Object deleted", "The object has been deleted.");
            } else {
                showErrorMessage("It doesn't exist a candidate with id given.");
            }
        }
    }


    //other methods
    private Candidate extractCandidate()
    {
        try {
            int id = Integer.parseInt(view.getTxtfieldID().getText());
            String firstName = view.getTxtfieldFirstName().getText();
            String lastName = view.getTxtfieldLastName().getText();
            String phoneNr = view.getTxtfieldPhoneNr().getText();
            String mail = view.getTxtfieldMail().getText();
            return new Candidate(id, firstName, lastName, phoneNr, mail);
        }catch(NumberFormatException e){
            showErrorMessage("Id has to be an integer");
        }
        return null;
    }
    static void showMessage(Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    static void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }


    public void showCandidateDetails(Candidate newValue)
    {
        if (newValue!=null)
        {
            view.getTxtfieldMail().setText(newValue.getMail());
            view.getTxtfieldFirstName().setText(newValue.getFirstName());
            view.getTxtfieldLastName().setText(newValue.getLastName());
            view.getTxtfieldPhoneNr().setText(newValue.getPhoneNr());
            view.getTxtfieldID().setText(Integer.toString(newValue.getId()));
        }
        else
        {
            view.getTxtfieldFirstName().setText("");
            view.getTxtfieldLastName().setText("");
            view.getTxtfieldPhoneNr().setText("");
            view.getTxtfieldMail().setText("");
            view.getTxtfieldID().setText("");
        }
    }

    private boolean fieldsEmpty(){
        return Objects.equals(view.getTxtfieldID().getText(), "") ||
                Objects.equals(view.getTxtfieldFirstName().getText(), "") ||
                Objects.equals(view.getTxtfieldLastName().getText(), "") ||
                Objects.equals(view.getTxtfieldPhoneNr().getText(), "") ||
                Objects.equals(view.getTxtfieldMail().getText(), "");
    }
}
