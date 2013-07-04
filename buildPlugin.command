#!/bin/sh
cd /Users/Florian/NetBeansProjects/UHC/dist/
mv UHC.jar UHC.zip
unzip UHC -d UHC
cp ../plugin.yml UHC/plugin.yml
#cp ../config.yml SignKit/config.yml
cd UHC
zip -r UHC *
mv UHC.zip ../UHC.zip
cd ..
mv UHC.zip /Users/Florian/Documents/Serveur/plugins/UHC.jar
cd /Users/Florian/Documents/Serveur
clear
java -jar server.jar