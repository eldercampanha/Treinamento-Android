package br.com.monitoratec.app.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import br.com.monitoratec.app.R;

/**
 * Entidade da API GitHub Status.
 *
 * @see <a href="https://status.github.com/api/last-message.json">Last Message</a>
 *
 * Created by falvojr on 1/9/17.
 */
public class Status {

    @SerializedName("status")
    public Type type;
    public String body;
    public Date created_on;

    public enum Type{

        @SerializedName("good")
        GOOD(R.color.colorGood,R.string.txt_loading),
        @SerializedName("minor")
        MINOR(R.color.colorMinor, R.string.txt_error),
        @SerializedName("major")
        MAJOR(R.color.colorMajor, R.string.txt_fail);

        private int colorId;
        private int message;

        Type(int id, int message) {
            this.colorId =id;
            this.message = message;
        }

        public int getColorId(){
            return colorId;
        }
        public int getMessageId(){
            return message;
        }
    }

}
