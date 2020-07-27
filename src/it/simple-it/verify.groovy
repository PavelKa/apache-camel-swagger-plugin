//println "BaseDir:" + basedir
File touchFile = new File( basedir, "target/classes/META-INF/cz.kahle.maven.SimpleTestRouter2.json" )
//println "Exists:" +  touchFile.exists()
//println "IsFile:" +  touchFile.isFile()
//println "FilePath:" + touchFile.getAbsolutePath()
//println touchFile.text
assert touchFile.exists()
assert  touchFile.isFile()
