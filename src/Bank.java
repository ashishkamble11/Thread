class Bank
{
    int balance;
    StringBuffer acc_Name;

    public Bank(int balance, String acc_Name)
    {
        this.balance=balance;
        this.acc_Name=new StringBuffer(acc_Name);
    }

    int credit(int amount)
    {
        balance += amount;
        System.out.println(Thread.currentThread().getName() + ":" + acc_Name + " says "+ amount + " Credited Successfully");

        return balance;
    }

    boolean debit(int amount)
    {
        if(amount > balance)
        {
            System.out.println(Thread.currentThread().getName()+"'s Balance is: "+checkBalance());
            System.out.println(Thread.currentThread().getName()+ ":"+acc_Name+" says "+amount+" greater than available balane.");
            System.out.println("Debit amount exceeded for "+Thread.currentThread().getName());
            return false;
        }
        balance -= amount;
        System.out.println(Thread.currentThread().getName()+ ":"+acc_Name+" says "+amount+" debited from available balane.");
        return true;
    }

    int checkBalance()
    {
        return balance;
    }
}
