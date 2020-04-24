import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder

/**
 * 右键运行跟运行 bin\gatling.bat 和bin\gatling.sh效果一致
 */
object Engine extends App {

  val props = new GatlingPropertiesBuilder()
    .resourcesDirectory(IDEPathHelper.resourcesDirectory.toString)
    .resultsDirectory(IDEPathHelper.resultsDirectory.toString)
    .binariesDirectory(IDEPathHelper.mavenBinariesDirectory.toString)

  Gatling.fromMap(props.build)
}
