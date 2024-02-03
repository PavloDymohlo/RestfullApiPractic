package internetStore.service;

import internetStore.dao.PhoneDao;
import internetStore.models.Phone;

import java.util.List;

public class PhoneService {
    private final PhoneDao phoneDao = PhoneDao.getInstance();

    public void savePhone(Phone phone) {
        phoneDao.savePhone(phone);
    }

    public Phone getById(int id) {
        return phoneDao.getById(id);
    }

    public void deleteById(int id) {
        phoneDao.deleteById(id);
    }

    public List<Phone> getAllPhones() {
        return phoneDao.getAllPhone();
    }
}