package adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pojo.dentista;

import com.example.dentistas.MainActivity;
import com.example.dentistas.R;
import com.example.dentistas.cardview;
import com.example.dentistas.inicio;

public class adaptadorver extends RecyclerView.Adapter<adaptadorver.ViewHolder> {

    private Context context;
    private List<dentista> dentistasList;

    // Constructor
    public adaptadorver(Context context, List<dentista> dentistasList) {
        this.context = context;
        this.dentistasList = dentistasList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño del item
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Obtener el objeto dentista actual
        dentista currentDentista = dentistasList.get(position);

        // Vincular los datos con las vistas del ViewHolder
        holder.tvNombre.setText(currentDentista.getNombrecompleto());
        holder.tvEspecialidad.setText(currentDentista.getEspecialidad());

        holder.tvNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, cardview.class);
                i.putExtra("posicion", position);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Devolver el tamaño de la lista
        return dentistasList.size();
    }

    // Clase ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvEspecialidad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Referenciar las vistas del layout del item
            tvNombre = itemView.findViewById(R.id.theview);
            tvEspecialidad = itemView.findViewById(R.id.thedescription);
        }
    }
}
