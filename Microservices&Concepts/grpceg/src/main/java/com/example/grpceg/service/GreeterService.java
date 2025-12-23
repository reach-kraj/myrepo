package com.example.grpceg.service;


import com.example.grpceg.GreeterGrpc;
import com.example.grpceg.GreeterProto;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
public class GreeterService extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(GreeterProto.HelloRequest req, StreamObserver<GreeterProto.HelloReply> responseObserver) {
        String message = "Hello, " + req.getName() + "!";
        GreeterProto.HelloReply reply = GreeterProto.HelloReply.newBuilder().setMessage(message).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}