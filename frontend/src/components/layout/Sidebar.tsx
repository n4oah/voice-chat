import { List, ListItem, ListItemButton, ListItemText } from '@mui/material';
import { grey } from '@mui/material/colors';

export function Sidebar() {
  return (
    <section>
      <List style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
        {['Home', '방1', 'asdfsadfijasdf'].map((text, index) => (
          <ListItem key={index} disablePadding>
            <ListItemButton
              style={{
                padding: 0,
                backgroundColor: grey[500],
                borderRadius: '50%',
                minWidth: '60px',
                width: '60px',
                height: '60px',
                textAlign: 'center',
                overflow: 'hidden',
              }}
            >
              <ListItemText primary={text} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </section>
  );
}
