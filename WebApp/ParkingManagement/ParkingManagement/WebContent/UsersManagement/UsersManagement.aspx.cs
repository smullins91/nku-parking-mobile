using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;


namespace ParkingManagement.WebContent.UsersManagement
{
    public partial class UsersManagement : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!Page.IsPostBack)
            {
                populateGridview();
                populateUserRoles(ddlUserRoles );
             
              
            }
           
        }

        /// <summary>
        /// Populate the gridview with user information
        /// </summary>
        private void populateGridview()
        {
            List<ManageUsers.SuperUser> dtUsers = ManageUsers.getUsers();
            gvUsers.DataSource = dtUsers;
            gvUsers.DataBind();
            Cache["User"] = gvUsers.DataSource;
        }


        /// <summary>
        /// Populate userRoles
        /// </summary>
        private void populateUserRoles(DropDownList ddlTarget)
        {
            List<ManageUsers.UserRoles> dtRoles = ManageUsers.getUserRoles();
            ddlTarget.DataSource = dtRoles;
            ddlTarget.DataTextField = "Description";
            ddlTarget.DataValueField = "RoleId";
            ddlTarget.DataBind();
        }
      
        /// <summary>
        /// To add a new user in the database
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        protected void btnSave_Click(object sender, EventArgs e)
        {
         
            ManageUsers.User newUser = new ManageUsers.User(txtUserName.Text, txtPassword.Text, Convert.ToInt32(ddlUserRoles.SelectedValue) ,
               txtFirstName.Text, txtLastName.Text, txtEmail.Text);
            ManageUsers.addUser(newUser);

            // go to the ManagerUsers page
            Response.Redirect("UsersManagement.aspx");
        }

        /// <summary>
        /// Depending on the user selection, the appropriate routine will run
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        protected void gvUsers_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            int index = Convert.ToInt32(e.CommandArgument);
            string strUserId = gvUsers.DataKeys[index].Value.ToString();
            hfUserId.Value = strUserId;
            if ( e.CommandName.Equals("deleteUser"))
            {
                System.Text.StringBuilder sb = new System.Text.StringBuilder();
                sb.Append(@"<script type='text/javascript'>");
                sb.Append("$('#deleteModal').modal('show');");
                sb.Append(@"</script>");
                ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "DeleteModalScript", sb.ToString(), false);
        
            }
            else if ( e.CommandName.Equals("editUser"))
            {
                populateUserRoles(ddlUserRoles1);
                //populate form
                getData(index);
                System.Text.StringBuilder sb = new System.Text.StringBuilder();
                sb.Append(@"<script type='text/javascript'>");
                sb.Append("$('#editModal').modal('show');");
                sb.Append(@"</script>");
                ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "EditModalScript", sb.ToString(), false);
                
            }
            else if (e.CommandName.Equals("resetPassword"))
            {
                System.Text.StringBuilder sb = new System.Text.StringBuilder();
                sb.Append(@"<script type='text/javascript'>");
                sb.Append("$('#resetModal').modal('show');");
                sb.Append(@"</script>");
                ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "ResetModalScript", sb.ToString(), false);
            }
        }


        /// <summary>
        /// Populate user form
        /// </summary>
        /// <param name="index"></param>
        private void getData( int index)
        {
            GridViewRow gvrow = gvUsers.Rows[index];
            // Get the user info
            txtUserName1.Text = gvrow.Cells[0].Text;
            txtLastName1.Text = gvrow.Cells[1].Text;
            txtFirstName1.Text = gvrow.Cells[2].Text;
            txtEmail1.Text = gvrow.Cells[3].Text;
            chkIsAdmin1.Checked = false;

            string Admin = gvrow.Cells[6].Text;
            if (Admin.Equals("Yes"))
                chkIsAdmin1.Checked = true;

        }

        /// <summary>
        /// To add a new user in the database
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        protected void btnSave1_Click(object sender, EventArgs e)
        {
            int isAdmin = 0;
            if (chkIsAdmin1.Checked)
                isAdmin = 1;

            // get the update user info
            ManageUsers.editUser u = new ManageUsers.editUser(Convert.ToInt32(hfUserId.Value), txtUserName1.Text.Trim(), Convert.ToInt32(ddlUserRoles1.SelectedValue) ,
                   txtFirstName1.Text.Trim(), txtLastName1.Text.Trim(), txtEmail1.Text.Trim(), isAdmin,string.Empty);

            ManageUsers.updateUser(u);

            // go to the ManagerUsers page
            Response.Redirect("UsersManagement.aspx");
        }

        protected void btnDelete_Click(object sender, EventArgs e)
        {
           ManageUsers.deleteUser(Convert.ToInt32(hfUserId.Value));
           Response.Redirect("UsersManagement.aspx");

        }

        private void editUser()
        {
            ManageUsers.User newUser = new ManageUsers.User(txtUserName.Text, txtPassword.Text, Convert.ToInt32(ddlUserRoles.SelectedValue),
               txtFirstName.Text, txtLastName.Text, txtEmail.Text);
            ManageUsers.addUser(newUser);

            // go to the ManagerUsers page
            Response.Redirect("UsersManagement.aspx");
        }

        protected void gvUsers_PageIndexChanging(object sender, GridViewPageEventArgs e)
        {
            gvUsers.PageIndex = e.NewPageIndex;
            PageChangeData(); 

        }
        protected void PageChangeData()
        {
            List<ManageUsers.SuperUser> dtUsers = (List<ManageUsers.SuperUser>)Cache["User"];
            gvUsers.DataSource = dtUsers;
            gvUsers.DataBind();

        }

        protected void btnSavePassword_Click(object sender, EventArgs e)
        {
           

        }

      
       
    }
}