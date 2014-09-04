#!/bin/sh

S=`pwd`

# Generate constants_local.py for flux app.
echo "1. Setup constants for MicrobesFlux"
echo '' > flux/constants_blueprint.py
echo "kegg_database = \"${S}/kegg_database/\" " >> flux/constants_blueprint.py
echo "baseurl = \"${S}/flux/\" " >> flux/constants_blueprint.py
cd flux
rm -f constants.py
mv constants_blueprint.py constants.py
cd ..

echo "2. Rewrite settings_local.py under microbesflux"
mkdir -p session
mkdir -p media

if [[ ! -f microbesflux/settings_local.py ]]; then
 touch microbesflux/settings_local.py 
 echo "MEDIA_ROOT " >> microbesflux/settings_local.py 
 echo "SESSION_FILE_PATH " >> microbesflux/settings_local.py 
fi

# Change Django settings.
platform=`uname`
if [[ $platform == 'Darwin' ]]; then
	sed -i "" "s%^MEDIA_ROOT.*\$%MEDIA_ROOT = \'${PWD}/media/\'%g " microbesflux/settings_local.py
	sed -i "" "s%^SESSION_FILE_PATH.*\$%SESSION_FILE_PATH = \'${PWD}/session/\'%g " microbesflux/settings_local.py
else 
	sed -i "s%^MEDIA_ROOT.*\$%MEDIA_ROOT = \'${PWD}/media/\'%g " microbesflux/settings_local.py
	sed -i "s%^SESSION_FILE_PATH.*\$%SESSION_FILE_PATH = \'${PWD}/session/\'%g " microbesflux/settings_local.py
fi
#SESSION_FILE_PATH
