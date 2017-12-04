package view;

import controllers.CandidateController;
import domain.Candidate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CandidateView {

    private CandidateController ctrl;
    private TextField txtfieldID = new TextField();
    private TextField txtfieldFirstName = new TextField();
    private TextField txtfieldLastName = new TextField();
    private TextField txtfieldPhoneNr = new TextField();
    private TextField txtfieldMail = new TextField();

    public CandidateView(CandidateController ctrl) {
        this.ctrl = ctrl;
        //init the view
        initView();
    }

    public TextField getTxtfieldID() {
        return txtfieldID;
    }

    public TextField getTxtfieldFirstName() {
        return txtfieldFirstName;
    }

    public TextField getTxtfieldLastName() {
        return txtfieldLastName;
    }

    public TextField getTxtfieldPhoneNr() {
        return txtfieldPhoneNr;
    }

    public TextField getTxtfieldMail() {
        return txtfieldMail;
    }

    private BorderPane borderPane;

    TableView<Candidate> tableView=new TableView<>();


    private void initView() {

        borderPane=new BorderPane();
        //top AnchorPane
        borderPane.setTop(initTop());
        //left
        borderPane.setLeft(initCenter());
        //center
        borderPane.setCenter(initLeft());

    }


    private AnchorPane initTop()
    {
        AnchorPane topAnchorPane=new AnchorPane();
        ImageView img=new ImageView(new Image("logo.png"));

        img.setFitHeight(100);
        img.setFitWidth(100);
        img.setPreserveRatio(true);

        topAnchorPane.getChildren().add(img);
        //topAnchorPane.setPadding(new Insets(0,0,50,0));

        Label titleLabel=new Label("Candidates Menu");
        titleLabel.setFont(Font.font("Comic Sans MS"));
        topAnchorPane.getChildren().add(titleLabel);
        AnchorPane.setTopAnchor(titleLabel,20d);
        AnchorPane.setLeftAnchor(titleLabel,100d);
        titleLabel.setFont(new Font(25));
        return topAnchorPane;
    }

    public AnchorPane initLeft(){
        AnchorPane leftAnchorPane=new AnchorPane();
        tableView=createCandidateTableView();
        tableView.setMinWidth(380);

        //tableView.setStyle("-fx-background-color: bisque;"+"-fx-border-color: burlywood;"+"-fx-border-width: 1;");
        leftAnchorPane.getChildren().add(tableView);
        AnchorPane.setLeftAnchor(tableView,20d);
        return  leftAnchorPane;
    }
    public AnchorPane initCenter()
    {
        AnchorPane centerAnchorPane=new AnchorPane();
        centerAnchorPane.setPadding(new Insets(0,0,0,20));
        GridPane gridPane = createGridPane();
        AnchorPane.setRightAnchor(gridPane,20d);
        centerAnchorPane.getChildren().add(gridPane);

        String style = "-fx-background-color: bisque;"+"-fx-border-color: burlywood;"+"-fx-border-width: 1;"+"-fx-font-weight: bold";
        HBox buttonZone = new HBox();
        buttonZone.setSpacing(10);
        buttonZone.setPadding(new Insets(200,0,0,70));
        Button addButton = new Button("Add");
        addButton.setOnAction(ctrl::handleAddMessage);
        addButton.setStyle(style);
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle(style);
        deleteButton.setOnAction(ctrl::handleDeleteMessage);
        Button updateButton = new Button("Update");
        updateButton.setStyle(style);
        updateButton.setOnAction(ctrl::handleUpdateMessage);
        Button clearButton = new Button("Clear Fields");
        clearButton.setStyle(style);
        clearButton.setOnAction(ctrl::handleClearFields);
        buttonZone.getChildren().addAll(addButton,deleteButton,updateButton,clearButton);
        //AnchorPane.setBottomAnchor(buttonZone,100d);

        centerAnchorPane.getChildren().add(buttonZone);
        return centerAnchorPane;
    }

    private GridPane createGridPane()
    {
        String style = "-fx-background-color: bisque;"+"-fx-border-color: burlywood;"+"-fx-border-width: 1;";
        GridPane gp = new GridPane();
        gp.setStyle(style);
        gp.setPadding(new Insets(10,10,10,10));
        Border border = new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        gp.setBorder(border);
        Label labelID = createLabel("ID");
        Label labelFirstName = createLabel("First name:");
        Label labelLastName = createLabel("Last name:");
        Label labelPhoneNr = createLabel("Phone nr:");
        Label labelMail = createLabel("Mail:");
        gp.add(labelID,0,0);
        gp.add(this.txtfieldID,1,0);
        gp.add(labelFirstName,0,1);
        gp.add(this.txtfieldFirstName,1,1);
        gp.add(labelLastName,0,2);
        gp.add(this.txtfieldLastName,1,2);
        gp.add(labelPhoneNr,0,3);
        gp.add(this.txtfieldPhoneNr,1,3);
        gp.add(labelMail,0,4);
        gp.add(this.txtfieldMail,1,4);
        gp.setHgap(5);
        gp.setVgap(5);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPrefWidth(100);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPrefWidth(250);

        gp.getColumnConstraints().addAll(c1,c2);
        return gp;
    }


    private TableView<Candidate> createCandidateTableView()
    {
        TableColumn<Candidate,String> colFirstName = new TableColumn<>("First name");
        TableColumn<Candidate,String> colLastName = new TableColumn<>("Last name");
        TableColumn<Candidate,String> colPhoneNr = new TableColumn<>("PhoneNr");
        TableColumn<Candidate,String> colMail = new TableColumn<>("Mail");
        tableView.getColumns().addAll(colFirstName,colLastName,colPhoneNr,colMail);
        colFirstName.setCellValueFactory(new PropertyValueFactory<Candidate, String>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<Candidate, String>("lastName"));
        colPhoneNr.setCellValueFactory(new PropertyValueFactory<Candidate, String>("phoneNr"));
        colMail.setCellValueFactory(new PropertyValueFactory<Candidate, String>("mail"));
        tableView.setItems(ctrl.getModel());
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Candidate>() {
            @Override
            public void changed(ObservableValue<? extends Candidate> observable, Candidate oldValue, Candidate newValue)
            {
                txtfieldID.setDisable(true);
                ctrl.showCandidateDetails(newValue);
            }
        });
        return tableView;
    }


    public BorderPane getView(){ return borderPane;}

    //auxiliars methods

    private Label createLabel(String s){
        Label l=new Label(s);
        l.setFont(new Font(12));
        //l.setTextFill(Color.RED);
        l.setStyle("-fx-font-weight: bold");
        return l;
    }

}
