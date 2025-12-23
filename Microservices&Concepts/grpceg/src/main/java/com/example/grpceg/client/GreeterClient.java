package com.example.grpceg.client;


import com.example.grpceg.GreeterGrpc;
import com.example.grpceg.GreeterProto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component

public class GreeterClient {
    @GrpcClient("greeter")
    private GreeterGrpc.GreeterBlockingStub greeterStub;

    public String sayHello(String name) {
        GreeterProto.HelloRequest req = GreeterProto.HelloRequest.newBuilder().setName(name).build();
        GreeterProto.HelloReply rep = greeterStub.sayHello(req);
        return rep.getMessage();
    }
}
