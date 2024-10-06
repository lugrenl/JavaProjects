public class HomeWork {
    public static void main(String[] args) {

        SimpleDocent docent = new SimpleDocent();
        docent.name = "Mike";
        docent.age = 43;
        docent.height = 183;
        docent.department = "MAT";

        SimpleStudent student = new SimpleStudent();
        student.name = "Jeff";
        student.age = 19;
        student.height =178;
        student.department = "MAT";

        docent.describePerson();
        student.describePerson();

        student.sayHi();
        student.haveAParty();

        docent.sayHi();
        docent.makeStudentsSuffer();
    }
}
