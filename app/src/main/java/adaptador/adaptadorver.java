package adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import pojo.dentista;
import com.example.dentistas.R;
import com.example.dentistas.cardview;
public class adaptadorver extends RecyclerView.Adapter<adaptadorver.ViewHolder> {
    private final Context context;
    private final List<dentista> dentistasList;
    public adaptadorver(Context context, List<dentista> dentistasList) {
        this.context = context;
        this.dentistasList = dentistasList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Obtener el objeto dentista actual
        dentista currentDentista = dentistasList.get(position);

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
        // devolver el tamaño de la lista
        return dentistasList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvEspecialidad;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.theview);
            tvEspecialidad = itemView.findViewById(R.id.thedescription);
        }
    }
}
