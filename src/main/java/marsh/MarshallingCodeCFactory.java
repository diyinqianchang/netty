package marsh;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;


/**
 * @author zhangguolin
 * @description desc
 * @date 2020-12-03 10:25
 */
public class MarshallingCodeCFactory {


    public static MarshallingDecoder buildMarshallingDecoder(){

        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        DefaultUnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);

        MarshallingDecoder marshallingDecoder = new MarshallingDecoder(provider, 1024);
        return marshallingDecoder;
    }


    public static MarshallingEncoder buildMarshallingEncoder(){

        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        DefaultMarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);

        MarshallingEncoder marshallingEncoder = new MarshallingEncoder(provider);
        return marshallingEncoder;

    }

}
