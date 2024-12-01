package import_vault_item;

import entity.AbstractUser;
import exception.AuthException;
import mock.MockImportVaultItemPresenter;
import mock.MockUserRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.import_vault_item.ImportVaultItemInputData;
import service.import_vault_item.ImportVaultItemInteractor;

import static org.junit.jupiter.api.Assertions.*;

public class ImportVaultItemInteractorTest {
    private MockImportVaultItemPresenter presenter;
    private ImportVaultItemInteractor interactor;
    private MockUserRepository userRepository;
    private AbstractUser user;

    @BeforeEach
    public void setUp() throws AuthException {
        presenter = new MockImportVaultItemPresenter();
        interactor = new ImportVaultItemInteractor(presenter);
        userRepository = new MockUserRepository();
        user = userRepository.signupUser("test@example.com", "password");
    }

    @Test
    public void testImportVaultItems_Success() {
        String passwordManager = "1Password";
        JSONObject importJson = new JSONObject();
        importJson.put("item1", "value1");
        String importTextJson = importJson.toString();
        ImportVaultItemInputData inputData = new ImportVaultItemInputData(
                passwordManager,
                importTextJson,
                user,
                userRepository
        );
        interactor.importVaultItems(inputData);
        assertTrue(presenter.homeViewDisplayed);
        assertFalse(presenter.errorDisplayed);
    }

    @Test
    public void testImportVaultItems_InvalidPasswordManager() {
        String passwordManager = "InvalidManager";
        String importTextJson = "{}";
        ImportVaultItemInputData inputData = new ImportVaultItemInputData(
                passwordManager,
                importTextJson,
                user,
                userRepository
        );
        interactor.importVaultItems(inputData);
        assertFalse(presenter.homeViewDisplayed);
        assertTrue(presenter.errorDisplayed);
        assertEquals("Choose a password manager.", presenter.errorMessage);
    }

    @Test
    public void testImportVaultItems_InvalidJson() {
        String passwordManager = "1Password";
        String importTextJson = "{invalidJson";
        ImportVaultItemInputData inputData = new ImportVaultItemInputData(
                passwordManager,
                importTextJson,
                user,
                userRepository
        );
        interactor.importVaultItems(inputData);
        assertFalse(presenter.homeViewDisplayed);
        assertTrue(presenter.errorDisplayed);
        assertEquals("Invalid JSON.", presenter.errorMessage);
    }

    @Test
    public void testImportVaultItems_AddVaultItemsIOException() throws AuthException {
        String passwordManager = "1Password";
        userRepository.setThrowIOException(true);
        ImportVaultItemInputData inputData = new ImportVaultItemInputData(
                passwordManager,
                getMockInputJsonString(),
                user,
                userRepository
        );
        interactor.importVaultItems(inputData);
        assertFalse(presenter.homeViewDisplayed);
        assertTrue(presenter.errorDisplayed);
        assertEquals("Failed to import vault items to local vault.", presenter.errorMessage);
    }

    @Test
    public void testImportVaultItems_AddVaultItemsAuthException() throws AuthException {
        String passwordManager = "1Password";
        userRepository.setThrowAuthException(true);
        ImportVaultItemInputData inputData = new ImportVaultItemInputData(
                passwordManager,
                getMockInputJsonString(),
                user,
                userRepository
        );
        interactor.importVaultItems(inputData);
        assertFalse(presenter.homeViewDisplayed);
        assertTrue(presenter.errorDisplayed);
        assertEquals("Failed to import vault items to remote vault.", presenter.errorMessage);
    }

    private String getMockInputJsonString() {
        return "{\n" +
                "    \"accounts\": [{\n" +
                "        \"attrs\": {\n" +
                "            \"accountName\": \"Wendy Appleseed\",\n" +
                "            \"name\": \"Wendy Appleseed\",\n" +
                "            \"avatar\": \"profile-pic.png\",\n" +
                "            \"email\": \"wendy.c.appleseed@gmail.com\",\n" +
                "            \"uuid\": \"D4RI47B7BJDT25C2LWA7LEJLHZ\",\n" +
                "            \"domain\": \"https://my.1password.com/\"\n" +
                "        },\n" +
                "        \"vaults\": [{\n" +
                "            \"attrs\": {\n" +
                "                \"uuid\": \"rr3lr6c2opoggvrete23q72ahi\",\n" +
                "                \"desc\": \"\",\n" +
                "                \"avatar\": \"pic.png\",\n" +
                "                \"name\": \"Personal\",\n" +
                "                \"type\": \"P\"\n" +
                "            },\n" +
                "            \"items\": [{\n" +
                "                \"uuid\": \"fkruyzrldvizuqlnavfj3gltfe\",\n" +
                "                \"favIndex\": 1,\n" +
                "                \"createdAt\": 1614298956,\n" +
                "                \"updatedAt\": 1635346445,\n" +
                "                \"state\": \"active\",\n" +
                "                \"categoryUuid\": \"001\",\n" +
                "                \"details\": {\n" +
                "                    \"loginFields\": [{\n" +
                "                        \"value\": \"most-secure-password-ever!\",\n" +
                "                        \"id\": \"\",\n" +
                "                        \"name\": \"password\",\n" +
                "                        \"fieldType\": \"P\",\n" +
                "                        \"designation\": \"password\"\n" +
                "                    }],\n" +
                "                    \"notesPlain\": \"This is a note. *bold*! _italic_!\",\n" +
                "                    \"sections\": [{\n" +
                "                        \"title\": \"Security\",\n" +
                "                        \"name\": \"Section_oazxddhvftfknycbbmh5ntwfa4\",\n" +
                "                        \"fields\": [{\n" +
                "                            \"title\": \"PIN\",\n" +
                "                            \"id\": \"CCEF647B399604E8F6Q6C8C3W31AFD407\",\n" +
                "                            \"value\": {\n" +
                "                                \"concealed\": \"12345\"\n" +
                "                            },\n" +
                "                            \"indexAtSource\": 0,\n" +
                "                            \"guarded\": false,\n" +
                "                            \"multiline\": false,\n" +
                "                            \"dontGenerate\": false,\n" +
                "                            \"inputTraits\": {\n" +
                "                                \"keyboard\": \"default\",\n" +
                "                                \"correction\": \"default\",\n" +
                "                                \"capitalization\": \"default\"\n" +
                "                            }\n" +
                "                        }]\n" +
                "                    }],\n" +
                "                    \"passwordHistory\": [{\n" +
                "                        \"value\": \"12345password\",\n" +
                "                        \"time\": 1458322355\n" +
                "                    }],\n" +
                "                    \"documentAttributes\": {\n" +
                "                        \"fileName\": \"My movie.mp4\",\n" +
                "                        \"documentId\": \"o2xjvw2q5j2yx6rtpxfjdqopom\",\n" +
                "                        \"decryptedSize\": 3605932\n" +
                "                    }\n" +
                "                },\n" +
                "                \"overview\": {\n" +
                "                    \"subtitle\": \"\",\n" +
                "                    \"urls\": [{\n" +
                "                        \"label\": \"\",\n" +
                "                        \"url\": \"https://www.dropbox.com/\"\n" +
                "                    }],\n" +
                "                    \"title\": \"Dropbox\",\n" +
                "                    \"url\": \"https://www.dropbox.com/\",\n" +
                "                    \"ps\": 100,\n" +
                "                    \"pbe\": 86.13621,\n" +
                "                    \"pgrng\": true\n" +
                "                }\n" +
                "            }]\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

    }
}
