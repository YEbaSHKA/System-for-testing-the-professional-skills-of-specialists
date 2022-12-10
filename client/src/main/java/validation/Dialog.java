package validation;

import javafx.scene.control.Alert;

public class Dialog {
    static public void showAlertWithNullInput(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка: Ввод данных");
        alert.setContentText("Заполните пустые поля");
        alert.showAndWait();
    }

    static public void showAlertWithExistLogin(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка: Регистрация");
        alert.setContentText("Такой пользователь уже существует");
        alert.showAndWait();
    }

    static public void showAlertWithNoLogin(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка: Введите правильно логин или пароль");
        alert.setContentText("Такой пользователь не найден в системе");
        alert.showAndWait();
    }

    static public void showAlertWithUncorrectData()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Ошибка");
		alert.setHeaderText("Ошибка: Введите правильно логин или пароль");
		alert.setContentText("Допустимые символы: A-Z, a-z, 0-9, _");

		alert.showAndWait();
    }

    static public void showAlertWithUncorrectFullName()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка: Введите правильно ФИО");
		alert.setContentText("Пример: 'Иванов Иван Иванович'");

		alert.showAndWait();
    }

    static public void showAlertWithServerTrouble()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Ошибка сервера");
        alert.setHeaderText("Приносим свои извинения");
		alert.setContentText("Технические неполадки");

		alert.showAndWait();
    }

    static public void correctRegistration(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setHeaderText(null);
        alert.setContentText( "Регистрация прошла успешно");
        alert.showAndWait();
    }

    static public void successPass(String str)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setHeaderText(null);
        alert.setContentText( "Вы прошли тест на: " + str);
        alert.showAndWait();
    }

    // static public void showAlertWithData(){
    //     Alert alert = new Alert(Alert.AlertType.ERROR);
    //     alert.setTitle("Ошибка");
    //     alert.setHeaderText("Ошибка: Сбой задачи");
    //     alert.setContentText("Проверьте введнные параметры");
    //     alert.showAndWait();
    // }

    // static public void correctOperation(){
    //     Alert alert = new Alert(Alert.AlertType.INFORMATION);
    //     alert.setTitle("Correct");
    //     alert.setHeaderText("");
    //     alert.setContentText("Операция прошла успешно");
    //     alert.showAndWait();
    // }


}