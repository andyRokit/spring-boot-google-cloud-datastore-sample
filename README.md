### Google Cloud Datastore - Spring Boot Sample

WIP - attempting to add App Engine-provided authorization.
That worked, but still trying to figure out how to add roles based on Google Groups.

Deploying

    mvn appengine:stage
    // change target/appengine-staging/app.yaml to have:
    //   login: required
    //   secure: always
    gcloud app deploy target/appengine-staging/app.yaml