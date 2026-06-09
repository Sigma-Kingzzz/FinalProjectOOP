import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ZakatCalculatorScene {
    private final HBox rootLayout;
    private final IncomeZakatCalculator calculator = new IncomeZakatCalculator();

    public ZakatCalculatorScene(Employee currentUser) {
        rootLayout = new HBox(20);
        rootLayout.setPadding(new Insets(25));
        rootLayout.setStyle("-fx-background-color: #f4f6f9;");

        // ================= LEFT COLUMN: INPUT GRID =================
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(12);
        inputGrid.setVgap(12);
        inputGrid.setPadding(new Insets(15));
        inputGrid.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 8px; -fx-border-color: #e2e8f0; -fx-border-radius: 8px;");

        TextField txtNisab = new TextField("25000");
        TextField txtSalary = new TextField("0");
        TextField txtBonus = new TextField("0");
        TextField txtDividend = new TextField("0");
        TextField txtEpf = new TextField("0");
        TextField txtBasic = new TextField("0");
        TextField txtParents = new TextField("0");
        TextField txtMedEdu = new TextField("0");
        TextField txtOtherZakat = new TextField("0");

        Text headerLeft = new Text("Financial & Relief Particulars");
        headerLeft.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        inputGrid.add(headerLeft, 0, 0, 2, 1);

        inputGrid.add(new Label("Current State Nisab (RM):"), 0, 1);
        inputGrid.add(txtNisab, 1, 1);

        Label subA = new Label("A. Gross Annual Income");
        subA.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        inputGrid.add(subA, 0, 2, 2, 1);
        GridPane.setMargin(subA, new Insets(8, 0, 0, 0));

        inputGrid.add(new Label("Annual Salary & Allowances:"), 0, 3);
        inputGrid.add(txtSalary, 1, 3);
        inputGrid.add(new Label("Annual Bonus & Commissions:"), 0, 4);
        inputGrid.add(txtBonus, 1, 4);
        inputGrid.add(new Label("Annual Dividends & Royalties:"), 0, 5);
        inputGrid.add(txtDividend, 1, 5);

        Label subB = new Label("B. Allowed Deductions (Pelepasan)");
        subB.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        inputGrid.add(subB, 0, 6, 2, 1);
        GridPane.setMargin(subB, new Insets(8, 0, 0, 0));

        inputGrid.add(new Label("EPF / Pension Scheme:"), 0, 7);
        inputGrid.add(txtEpf, 1, 7);
        inputGrid.add(new Label("Basic Self & Family Care:"), 0, 8);
        inputGrid.add(txtBasic, 1, 8);
        inputGrid.add(new Label("Dependent Parents Exemption:"), 0, 9);
        inputGrid.add(txtParents, 1, 9);
        inputGrid.add(new Label("Medical / Education Expenses:"), 0, 10);
        inputGrid.add(txtMedEdu, 1, 10);
        inputGrid.add(new Label("Other Paid Zakat Contributions:"), 0, 11);
        inputGrid.add(txtOtherZakat, 1, 11);

        Button btnCalculate = new Button("Calculate Zakat");
        btnCalculate.setMaxWidth(Double.MAX_VALUE);
        btnCalculate.setStyle("-fx-background-color: #0f766e; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-cursor: hand;");
        inputGrid.add(btnCalculate, 0, 12, 2, 1);

        // ================= RIGHT COLUMN: LIVE REVENUE DISPLAY =================
        VBox receiptBox = new VBox(15);
        receiptBox.setMinWidth(320);
        receiptBox.setPadding(new Insets(20));
        receiptBox.setStyle("-fx-background-color: #1e293b; -fx-background-radius: 8px;");
        receiptBox.setAlignment(Pos.TOP_CENTER);

        Text headerRight = new Text("OFFICIAL ZAKAT SUMMARY");
        headerRight.setFill(javafx.scene.paint.Color.WHITE);
        headerRight.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));

        Label lblGrossIncome = new Label("RM 0.00");
        Label lblDeductions = new Label("RM 0.00");
        Label lblNetIncome = new Label("RM 0.00");
        Label lblStatus = new Label("PENDING CALCULATION");
        Label lblAnnualZakat = new Label("RM 0.00");
        Label lblMonthlyZakat = new Label("RM 0.00");

        VBox metricBox = new VBox(10);
        metricBox.getChildren().addAll(
                createOutputRow("Total Gross Income:", lblGrossIncome, "#cbd5e1"),
                createOutputRow("Total Deductions:", lblDeductions, "#cbd5e1"),
                createOutputRow("Net Assessable Income:", lblNetIncome, "#cbd5e1")
        );

        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #475569;");

        VBox statusBox = new VBox(5);
        statusBox.setAlignment(Pos.CENTER);
        statusBox.setPadding(new Insets(10));
        statusBox.setStyle("-fx-background-color: #334155; -fx-background-radius: 5px;");
        lblStatus.setTextFill(javafx.scene.paint.Color.YELLOW);
        lblStatus.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        statusBox.getChildren().addAll(new Label("ELIGIBILITY STATUS:"), lblStatus);
        statusBox.getChildren().get(0).setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 11px;");

        lblAnnualZakat.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblAnnualZakat.setTextFill(javafx.scene.paint.Color.LIGHTGREEN);
        lblMonthlyZakat.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        lblMonthlyZakat.setTextFill(javafx.scene.paint.Color.WHITE);

        VBox finalOutputs = new VBox(8);
        finalOutputs.setAlignment(Pos.CENTER);
        finalOutputs.getChildren().addAll(
                new Label("Annual Zakat Due:") {{ setStyle("-fx-text-fill: #94a3b8;"); }},
                lblAnnualZakat,
                new Label("Monthly Deduction equivalent:") {{ setStyle("-fx-text-fill: #94a3b8;"); }},
                lblMonthlyZakat
        );

        receiptBox.getChildren().addAll(headerRight, metricBox, separator, statusBox, finalOutputs);

        // --- Calculation Logic Binding ---
        btnCalculate.setOnAction(e -> {
            try {
                calculator.setCurrentNisab(Double.parseDouble(txtNisab.getText().trim()));
                calculator.getIncome(
                        Double.parseDouble(txtSalary.getText().trim()),
                        Double.parseDouble(txtBonus.getText().trim())
                );
                calculator.setDeductions(
                        Double.parseDouble(txtEpf.getText().trim())
                );

                lblGrossIncome.setText(String.format("RM %,.2f", calculator.calculateTotalGrossIncome()));
                lblDeductions.setText(String.format("RM %,.2f", calculator.calculateTotalDeductions()));
                lblNetIncome.setText(String.format("RM %,.2f", calculator.calculateNetIncome()));

                if (calculator.isNisabMet()) {
                    lblStatus.setText("OBLIGATORY (NISAB MET)");
                    lblStatus.setStyle("-fx-text-fill: #4ade80; -fx-font-weight: bold;");
                    lblAnnualZakat.setText(String.format("RM %,.2f", calculator.calculateAnnualZakat()));
                    lblMonthlyZakat.setText(String.format("RM %,.2f / month", calculator.calculateMonthlyZakat()));
                } else {
                    lblStatus.setText("EXEMPTED (BELOW NISAB)");
                    lblStatus.setStyle("-fx-text-fill: #f87171; -fx-font-weight: bold;");
                    lblAnnualZakat.setText("RM 0.00");
                    lblMonthlyZakat.setText("RM 0.00");
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid numeric values everywhere.");
                alert.showAndWait();
            }
        });

        rootLayout.getChildren().addAll(inputGrid, receiptBox);
    }

    private HBox createOutputRow(String labelText, Label dynamicValueLabel, String hexColor) {
        HBox row = new HBox();
        Label lblText = new Label(labelText);
        lblText.setStyle("-fx-text-fill: " + hexColor + ";");
        dynamicValueLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        row.getChildren().addAll(lblText, spacer, dynamicValueLabel);
        return row;
    }

    public HBox getLayout() {
        return rootLayout;
    }
}