package qu.moon.learn.service

interface Service<Val> {

    fun <Val> getService(): Val

}