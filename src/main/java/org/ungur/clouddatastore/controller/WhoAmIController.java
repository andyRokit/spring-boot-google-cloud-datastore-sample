package org.ungur.clouddatastore.controller;

import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.admin.directory.Directory;
import com.google.api.services.admin.directory.model.Groups;
import com.google.auth.appengine.AppEngineCredentials;
import com.google.auth.http.HttpCredentialsAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

@RestController
public class WhoAmIController {
    @GetMapping("/whoami")
    public Principal whoAmI(Principal principal) {
        return principal;
    }

    @GetMapping("/groups")
    public Groups groups() throws IOException {
        HttpCredentialsAdapter httpCredentialsAdapter = new HttpCredentialsAdapter(AppEngineCredentials.getApplicationDefault());

        HttpTransport transport = UrlFetchTransport.getDefaultInstance();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        Directory directory = new Directory.Builder(
                transport,
                jsonFactory,
                httpCredentialsAdapter).build();

        return directory.groups().list().execute();
    }
}
