#!/usr/bin/env bash
if [[ -f ".env" ]]; then
 echo "file exists"
 export $(cat .env | xargs)
fi
export ORG_GRADLE_PROJECT_version=$(date +%Y.%-m.%-d%H%M%S)
./gradlew check buildPlugin patchRepositoryXml && \
curl --max-time 15 -T "{build/libs/PhpClean.jar,build/libs/PhpClean-nightly.xml}" $DEPLOY_URI