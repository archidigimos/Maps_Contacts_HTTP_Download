package com.example.archismansarkar.mapsandcontacts;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.provider.ContactsContract;
import android.widget.Toast;

public class ContactsManipulation extends Activity{
    public static void addContact(Context ctx, String displayname, String homenumber,
                    String mobilenumber, String worknumber, String homeemail,
                    String workemail, String companyname, String jobtitle) {
        String DisplayName = displayname;
        String MobileNumber = homenumber;
        String HomeNumber = mobilenumber;
        String WorkNumber = worknumber;
        String homeemailID = homeemail;
        String workemailID = workemail;
        String company = companyname;
        String jobTitle = jobtitle;
        ArrayList<ContentProviderOperation> contentProviderOperation = new
                ArrayList<ContentProviderOperation>();

        contentProviderOperation.add(ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        // ------------------------------------------------------ Names
        if (DisplayName != null) {
            contentProviderOperation.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                            ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            DisplayName).build());
        }

        // ------------------------------------------------------ Mobile Number
        if (MobileNumber != null) {
            contentProviderOperation.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                            ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
                            MobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }

        // ------------------------------------------------------ Home Numbers
        if (HomeNumber != null) {
            contentProviderOperation.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                            ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
                            HomeNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                    .build());
        }

        // ------------------------------------------------------ Work Numbers
        if (WorkNumber != null) {
            contentProviderOperation.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                            ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
                            WorkNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                    .build());
        }

        // ------------------------------------------------------ workEmail
        if (workemailID != null) {
            contentProviderOperation.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                            ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA,
                            workemailID)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
                            ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());
        }
        // ------------------------------------------------------ homeEmail
        if (homeemailID != null) {
            contentProviderOperation.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                            ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA,
                            homeemailID)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
                            ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                    .build());
        }
        // ------------------------------------------------------ Organization
        if (!company.equals("") && !jobTitle.equals("")) {
            contentProviderOperation.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                            ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.Organization.COMPANY,
                            company)
                    .withValue(
                            ContactsContract.CommonDataKinds.Organization.TYPE,
                            ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .withValue(
                            ContactsContract.CommonDataKinds.Organization.TITLE,
                            jobTitle)
                    .withValue(
                            ContactsContract.CommonDataKinds.Organization.TYPE,
                            ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                    .build());
        }
        // Asking the Contact provider to create a new contact
        try {
            ctx.getContentResolver()
                    .applyBatch(ContactsContract.AUTHORITY, contentProviderOperation);
        } catch (Exception e) {
            e.printStackTrace();
            //show exception in toast
            Toast.makeText(ctx, "Exception: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}