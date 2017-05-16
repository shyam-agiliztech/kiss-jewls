
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object home_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._

class home extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*1.1*/("""<!DOCTYPE html>
<html lang="en">

<head></head>


<body>

	<form action="getapiresponse" method="POST">
	
		Enter Comma Seperated SKU IDs :  <input type="text" name="sku" id="sku"><br/>
		<input type="submit" name="submit" id="submit" value="Download">
	
	</form>

</body>

</html>"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


}

/**/
object home extends home_Scope0.home
              /*
                  -- GENERATED --
                  DATE: Tue May 16 11:12:29 IST 2017
                  SOURCE: /Users/shyamrajsampath/Documents/work/java-play-workspace/kiss-jewls/app/views/home.scala.html
                  HASH: ba3edda5bb87a1cbdda2d7131465737debe4ac6b
                  MATRIX: 825->0
                  LINES: 32->1
                  -- GENERATED --
              */
          