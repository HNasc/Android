package br.edu.fadergs.tarefasassincronas;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostmonService {

    @GET("{cep}")
    Call<Endereco> getEndereco(@Path("cep") String cep);
}