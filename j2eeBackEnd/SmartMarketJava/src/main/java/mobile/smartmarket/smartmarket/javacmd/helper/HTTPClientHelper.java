package mobile.smartmarket.smartmarket.javacmd.helper;

public interface HTTPClientHelper {
	public abstract int sendPush(String idgcm, String idcard, String vendedor, Double monto);
}
