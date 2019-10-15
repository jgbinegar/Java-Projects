cd "`dirname "$0"`"

echo 'Enter Version: '
read version
echo 'Enter Filename: '
read fileName

rm Manifest.mft

cat <<EOT >> Manifest.mft
Manifest-Version: $version
Main-Class: Main
ClassPath: $fileName.jar
EOT

jar -cvfm $fileName.jar Manifest.mft images *.class

java -jar $fileName.jar