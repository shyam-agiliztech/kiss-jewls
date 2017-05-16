
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/shyamrajsampath/Documents/work/java-play-workspace/kiss-jewls/conf/routes
// @DATE:Tue May 16 11:12:29 IST 2017


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
