package Main;

import Controller.*;
import DAO.*;
import Model.*;
import View.*;

public class Main {
    public static void main(String[] args) {
        try {
            MainView mainView = new MainView();
            EmployeView viewEmployee = mainView.getEmployeeView();
            HolidayView viewHoliday = mainView.getHolidayPanel();
            
            HolidayImpl daoHoliday = new HolidayImpl();
            EmployeImpl daoEmployee = new EmployeImpl();
           ImportData da = new ImportData();
           Import daoo=new Import();
           //ImportData dao 
            HolidayModel modelHoliday = new HolidayModel(daoHoliday, daoEmployee,daoo);
            EmployeModel modelEmployee = new EmployeModel(daoEmployee ,da);
            
            HolidayController holidayController = new HolidayController(viewHoliday, modelHoliday);
            EmployeController employeeController = new EmployeController(modelEmployee, viewEmployee);
            
            mainView.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}