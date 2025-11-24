package co.peanech.onboardingtask

object Constants {
    // extra
    const val EXTRA_EMAIL = "extra_email"
    const val EXTRA_PASSWORD = "extra_PASSWORD"
    const val EXTRA_USER = "extra_user"

    // regex
    const val REGEX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
    const val REGEX_PASSWORD = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}\$"
}
