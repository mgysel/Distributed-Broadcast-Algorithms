package cs451;
import java.util.List;

public class BroadcastConfig {
    public int M;
    public Host me;
    public List<Config> configs;
    public Hosts hosts;

    public BroadcastConfig(int M, Host me, List<Config> configs, Hosts hosts) {
        this.M = M;
        this.me = me;
        this.configs = configs;
        this.hosts = hosts;
    }

    public int getM() {
        return M;
    }

    public Host getMe() {
        return me;
    }

    public List<Config> getConfigs() {
        return configs;
    }

    public Hosts getHosts() {
        return hosts;
    }
}