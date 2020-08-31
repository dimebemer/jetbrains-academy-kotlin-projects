// write the BankAccount class here
data class BankAccount(val deposited: Long, val withdrawn: Long) {
    val balance: Long = deposited - withdrawn
}