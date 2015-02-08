/**
 * Created by FScoward on 2015/02/08.
 */

import java.io.{FileOutputStream, OutputStreamWriter, PrintWriter, InputStreamReader, BufferedReader}

import scala.sys.process.{ProcessIO, Process}

object Main {

  def main(args: Array[String]) {
    val filename = "gijiroku"
    
    val RESOURCES = "./src/main/resources"
    
    Process(s"$RESOURCES/xdoc2txt.exe -f -8 $filename.pdf") !

    val pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("out.txt"), "UTF-8"))
    
    val pio = new ProcessIO(
      in => {},
      out => {
        val reader = new BufferedReader(new InputStreamReader(out, "UTF-8"))
        Iterator.continually(reader.readLine()).takeWhile(_ != null).foreach(x => {pw.println(x)})
      },
      err => {})
    
    val p = Process(s"$RESOURCES/redpen-cli-1.0/bin/redpen.bat -c $RESOURCES/redpen-cli-1.0/conf/redpen-conf-ja.xml $filename.txt").run(pio)
    p.exitValue()
    pw.close()
  }
}
