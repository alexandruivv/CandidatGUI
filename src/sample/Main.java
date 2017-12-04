package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repositories.CandidateFileRepository;
import services.CandidateService;
import validators.CandidateValidator;
import controllers.CandidateController;
import view.CandidateView;


public class Main extends Application
{

    CandidateFileRepository repo=new CandidateFileRepository("./src/data/Candidates.txt",new CandidateValidator());
    CandidateService ser=new CandidateService(repo);

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Candidates");
        primaryStage.setScene(new Scene(initView(), 850, 600));
        primaryStage.show();
    }

    private Parent initView()
    {
        CandidateController ctrl=new CandidateController(ser);
        ser.addObserver(ctrl);
        CandidateView view=new CandidateView(ctrl);
        ctrl.setView(view);
        return view.getView();
    }

    public static void Main(String[] args) {
        launch(args);
    }
}