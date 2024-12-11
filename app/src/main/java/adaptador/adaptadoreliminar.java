package adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentistas.R;

import java.util.List;

import pojo.dentista;

public class adaptadoreliminar extends RecyclerView.Adapter<adaptadoreliminar.ViewHolder> {

    private Context context;
    private List<dentista> dentistaList;

    // Constructor
    public adaptadoreliminar(Context context, List<dentista> dentistaList) {
        this.context = context;
        this.dentistaList = dentistaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño del ViewHolder
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Obtener el objeto dentista actual
        dentista currentDentista = dentistaList.get(position);

        // Configurar los datos en las vistas
        holder.nombre.setText(currentDentista.getNombrecompleto());
        holder.check.setChecked(currentDentista.isChecked());

        // Manejar el cambio de estado del checkbox
        holder.check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Guardar el estado del checkbox en el objeto dentista
            currentDentista.setChecked(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        // Devolver el tamaño de la lista
        return dentistaList.size();
    }

    // Clase ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        CheckBox check;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Referenciar las vistas del layout del item
            nombre = itemView.findViewById(R.id.theview2); // Asegúrate de que este ID coincida con tu diseño
            check = itemView.findViewById(R.id.check); // Asegúrate de que este ID coincida con tu diseño
        }
    }
}
