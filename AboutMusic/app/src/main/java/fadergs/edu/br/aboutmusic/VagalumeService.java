package fadergs.edu.br.aboutmusic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VagalumeService {
    @GET("{musica}")
    Call<Musica> getMusica(@Path("musica") String musica);
}
