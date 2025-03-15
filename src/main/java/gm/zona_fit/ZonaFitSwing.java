package gm.zona_fit;

import com.formdev.flatlaf.FlatDarculaLaf;
import gm.zona_fit.gui.ZonaFitForma;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import javax.swing.*;

@SpringBootApplication
public class ZonaFitSwing {
    public static void main(String[] args) {
        FlatDarculaLaf.setup();

        // Instancia de la fabrica de spring
        ConfigurableApplicationContext contextoSpring =
                new SpringApplicationBuilder(ZonaFitSwing.class)
                        .headless(false) //Permite que la aplicación tenga UI
                        .web(WebApplicationType.NONE) //No usa un servidor web
                        .run(args);

        /* Creamos un objeto de Swing
        * Ejecutamos la app una vez que la fabrica de spring este cargada
        * Esto se realiza de esta manera ya que necesitamos que Swing se
        * ejecute en el hilo de eventos de Swing (Event Dispatch Thread)
        * y no en el hilo principal de spring
        */
        SwingUtilities.invokeLater(()-> {
            /* Es lo mismo que haciamos antes
            * ZonaFitForma zonaFitForma = new ZonaFitForma();
            * pero no lo podemos hacer asi ya que sino no podriamos inyectar las
            * dependencia de los servicios (Autowired)
            */
            ZonaFitForma zonaFitForma = contextoSpring.getBean(ZonaFitForma.class);
            zonaFitForma.setVisible(true);
        });
    }
}
