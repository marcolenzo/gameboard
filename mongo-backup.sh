#!/bin/bash

echo Archiving current mongo...
mongodump --db gameboard --archive > gameboard.archive

echo Moving archive to $HOME/backups/
mv gameboard.archive $HOME/backups/

echo Done.
