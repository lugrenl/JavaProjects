public class Main {

    public static String accountName = "User";
    public static Double amount = 100000.0;

    public static void main(String[] args) throws NotEnoughMoneyException {
        try{
            System.out.println(deduct("User", 900.0));
        } catch (NotEnoughMoneyException e) {
            System.out.println("Денег нет обработано");
        }

    }

    public static Double deduct(String accountToProcess, Double amtToCacheOut) throws NotEnoughMoneyException {
        if (!accountName.equals(accountToProcess)){
            throw new WrongNameException("Имя аккаунта не совпадает!");
        }

        if (amount < amtToCacheOut){
            throw new NotEnoughMoneyException("Недостаточный баланс счёта");
        }
        amount = amount - amtToCacheOut;
        return amount;
    }


}
