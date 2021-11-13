version=$1
artifact='SocketWrench'

mvn install:install-file -DgroupId=com.stuintech -DartifactId=$artifact -Dversion=$version -Dfile=../repository/com/stuintech/$artifact/$version/$artifact-$version.jar -Dpackaging=jar -DlocalRepositoryPath=. -DcreateChecksum=true -DgeneratePom=true
