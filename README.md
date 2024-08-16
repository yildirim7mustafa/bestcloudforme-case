# Bestcloudforme Case

Bu proje, belirli istekleri yanıtlayan bir API geliştirmeyi, Docker üzerinde çalıştırmayı, Docker Hub'a yüklemeyi ve Kubernetes cluster üzerine deploy etmeyi kapsamaktadır. Proje ayrıca, uygulamaya dışarıdan erişim sağlamak için bir Load Balancer kullanmayı içerir.

## Adımlar

### 1. API Yazımı
API, aşağıdaki işlevlere sahiptir:
- **GET /**: `{ "msg": "BC4M" }` cevabını döner.
- **GET /health**: Uygulamanın sağlık durumu kontrol edilir.
- **POST /**: İsteğin body’sinde gelen verileri geri döner.

### 2. Dockerfile Oluşturulması
Uygulamanın Docker üzerinde çalışabilmesi için bir Dockerfile oluşturulmuş ve proje deposuna eklenmiştir.

### 3. Docker Image Oluşturulması ve Docker Hub'a Yüklenmesi
Dockerfile kullanılarak bir Docker Image oluşturulmuş ve bu image, Docker Hub'daki kişisel repoya yüklenmiştir. 
1. **Yüklenen imaje repositorysi:**
   ```bash
   docker pull yildirim7mustafa/bestcloudforme

### 4. Kubernetes Cluster Kurulumu
Amazon web servicelerinden eks servisi ile kubernetes clusterı kurulmuştur. Network ayarları http 80 https 443 portları açılmıştır. Node group olarak t3 makine ve 1 node olarak eklenmiştir.
![image](https://github.com/user-attachments/assets/43966764-d91c-478a-b3e0-6fd07b1eed6d)

### 5. Uygulama Deploymentı
Uygulama, Kubernetes cluster üzerine deploy edilmiştir. `/health` endpoint'i cevap vermediğinde uygulama otomatik olarak yeniden başlatılacak şekilde yapılandırılmıştır.
1. **Terminal çıktısı:**
```bash
NAME                                  READY   STATUS    RESTARTS   AGE
pod/bestcloudforme-5b5cf6b6c6-gpfmf   1/1     Running   0          22m

NAME                             TYPE           CLUSTER-IP       EXTERNAL-IP                                                              PORT(S)          AGE
service/bestcloudforme-service   LoadBalancer   10.100.137.133   ad78f111824394ac2929f3d78d708329-532103383.eu-west-3.elb.amazonaws.com   8080:30479/TCP   23h

NAME                             READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/bestcloudforme   1/1     1            1           23h

NAME                                        DESIRED   CURRENT   READY   AGE
replicaset.apps/bestcloudforme-5b5cf6b6c6   1         1         1       23h
replicaset.apps/bestcloudforme-75d6c6858c   0         0         0       23m

NAME                                       CLASS    HOSTS                                    ADDRESS                                                                  PORTS     AGE
ingress.networking.k8s.io/bestcloudforme   <none>   bestcloudforme.yildirim7mustafa.online   ae5e49e37046e4caab98edee2df80f30-575788266.eu-west-3.elb.amazonaws.com   80, 443   23h
```

### 6. Uygulamaya Dışarıdan Erişim Sağlanması
Burada bc4m clusterım için cert-manager ve ingress-nginx kurulumu tamamlanmıştır. Go daddyden aldığım yildirim7mustafa.online domainim route53 servisine transfer edilmiştir. A kaydı olarak bestcloudforme eklenmiştir.Ingressime annotationlar eklenerek https kullanımı için sertifika kurulumu amaçlanmıştır. Ama ingress-nginx-controller üzerinden bestcloudforme ingressim dış dünyaya açılamamıştır.Network veya firewall ayarlarımda sorun olabilir çözümlemeye çalışıyorum.

### 7. Pipeline
Burası projelerde oluşturup kullandığım pipeline.Çok ayrıntı vermeden jenkinsfile ve yapıyı de eklemek istedim.
Yeni bir commit gelince bu commit jenkins'i trigger ediyor. Pipeline çalışıp build ediyoe ve CommitID ile tagleyerek yazılım takibi yapılıyor.Jenkins commit id'sini git adresinde tutulan (genelde baska bir projede yada projenin farklı bir branchinde) image'ın tagi commit id ile guncelleniyor.Jenkins success veya fail durumunu slack bildirimi ile gönderiyor.Argocd git adresini gözlediği için değişiklik gelince sync ediyor ve release sağlanmış oluyor. 
![image](https://github.com/user-attachments/assets/4e8ecbd3-eeaa-424b-ad12-95f2adc0a2bc)


