package internetStore.dao;

import internetStore.models.Phone;

import java.util.ArrayList;
import java.util.List;

public final class PhoneDao {
    private static final PhoneDao INSTANCE = new PhoneDao();
    private final List<Phone> phoneList = new ArrayList<>();
    private int phoneIdCounter = 1;

    public static PhoneDao getInstance() {
        return INSTANCE;
    }

    private PhoneDao() {
    }

    public void savePhone(Phone phone) {
        phone.setId(phoneIdCounter++);
        phoneList.add(phone);
    }

    public Phone getById(int id) {
        return phoneList.stream().filter(phone -> phone.getId() == id).findAny().orElse(null);
    }

    public void deleteById(int id) {
        Phone phoneSearch = phoneList.stream().filter(phone -> phone.getId() == id).findAny().orElse(null);
        if (phoneSearch != null) {
            phoneList.remove(phoneSearch);
        }
    }

    public List<Phone> getAllPhone() {
        return new ArrayList<>(phoneList);
    }
}

