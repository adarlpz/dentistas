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
    private final Context context;
    private final List<dentista> dentistaList;
    public adaptadoreliminar(Context context, List<dentista> dentistaList) {
        this.context = context;
        this.dentistaList = dentistaList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder2, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        dentista currentDentista = dentistaList.get(position);

        holder.nombre.setText(currentDentista.getNombrecompleto());
        holder.check.setChecked(currentDentista.isChecked());

        // manejar el cambio de estado del checkbox
        holder.check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // guardar el estado del checkbox en el objeto dentista
            currentDentista.setChecked(isChecked);
        });
    }
    @Override
    public int getItemCount() {
        // devolver el tamaño de la lista
        return dentistaList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        CheckBox check;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.theview2);
            check = itemView.findViewById(R.id.check);
        }
    }
}