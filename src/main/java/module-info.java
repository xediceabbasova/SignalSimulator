module com.company.furye {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.company.furye to javafx.fxml;
    exports com.company.furye;
}