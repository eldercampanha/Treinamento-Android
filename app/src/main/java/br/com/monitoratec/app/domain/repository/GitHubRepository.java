package br.com.monitoratec.app.domain.repository;

import java.util.List;

import br.com.monitoratec.app.domain.entity.User;
import rx.Observable;

/**
 * Created by elder-dell on 2017-01-11.
 */

public interface GitHubRepository {
    Observable<User> getUser(String credential);
    Observable<List<User>> getFollowers(String credential);
    void savePreferences(String token);
}
